package com.medallia.word2vec;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Doubles;
import com.medallia.word2vec.util.Pair;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Implementation of {@link Searcher} */
class SearcherImpl implements Searcher {
	private final NormalizedWord2VecModel model;
	private final ImmutableMap<String, Integer> word2vectorOffset;

	SearcherImpl(final NormalizedWord2VecModel model) {
		this.model = model;

		final ImmutableMap.Builder<String, Integer> result = ImmutableMap.builder();
		for (int i = 0; i < model.vocab.size(); i++) {
			result.put(model.vocab.get(i), i * model.layerSize);
		}

		word2vectorOffset = result.build();
	}

	SearcherImpl(final Word2VecModel model) {
		this(NormalizedWord2VecModel.fromWord2VecModel(model));
	}

	@Override
	public List<Match> getMatches(String s, int maxNumMatches) throws UnknownWordException {
		return getMatches(getVector(s), maxNumMatches);
	}

	@Override
	public double cosineDistance(String s1, String s2) throws UnknownWordException {
		return calculateDistance(getVector(s1), getVector(s2));
	}

	@Override
	public boolean contains(String word) {
		return word2vectorOffset.containsKey(word);
	}

	@Override
	public List<Match> getMatches(final double[] vec, int maxNumMatches) {
		return Match.ORDERING.greatestOf(
				Iterables.transform(model.vocab, new Function<String, Match>() {
					@Override
					public Match apply(String other) {
						double[] otherVec = getVectorOrNull(other);
						double d = calculateDistance(otherVec, vec);
						return new MatchImpl(other, d);
					}
				}),
				maxNumMatches
		);
	}

	private double calculateDistance(double[] otherVec, double[] vec) {
		double d = 0;
		for (int a = 0; a < model.layerSize; a++)
			d += vec[a] * otherVec[a];
		return d;
	}

	@Override
	public ImmutableList<Double> getRawVector(String word) throws UnknownWordException {
		return ImmutableList.copyOf(Doubles.asList(getVector(word)));
	}

	/**
	 * @return Vector for the given word
	 * @throws UnknownWordException If word is not in the model's vocabulary
	 */

	@Override
	public double[] getVector(String word) throws UnknownWordException {
		final double[] result = getVectorOrNull(word);
		if (result == null)
			throw new UnknownWordException(word);

		return result;
	}

	private double[] getVectorOrNull(final String word) {
		final Integer index = word2vectorOffset.get(word);
		if (index == null)
			return null;

		final DoubleBuffer vectors = model.vectors.duplicate();
		double[] result = new double[model.layerSize];
		vectors.position(index);
		vectors.get(result);
		return result;
	}

	/**
	 * @return Vector difference from v1 to v2
	 */
	private double[] getDifference(double[] v1, double[] v2) {
		double[] diff = new double[model.layerSize];
		for (int i = 0; i < model.layerSize; i++)
			diff[i] = v1[i] - v2[i];
		return diff;
	}

	@Override
	public SemanticDifference similarity(String s1, String s2) throws UnknownWordException {
		double[] v1 = getVector(s1);
		double[] v2 = getVector(s2);
		final double[] diff = getDifference(v1, v2);

		return new SemanticDifference() {
			@Override
			public List<Match> getMatches(String word, int maxMatches) throws UnknownWordException {
				double[] target = getDifference(getVector(word), diff);
				return SearcherImpl.this.getMatches(target, maxMatches);
			}
		};
	}

	/**
	 * Implementation of {@link Match}
	 */
	private static class MatchImpl extends Pair<String, Double> implements Match {
		private MatchImpl(String first, Double second) {
			super(first, second);
		}

		@Override
		public String match() {
			return first;
		}

		@Override
		public double distance() {
			return second;
		}

		@Override
		public String toString() {
			return String.format("%s [%s]", first, second);
		}
	}

	//check xem la so hay ko
	@Override public boolean checkValidDouble(String d){
		try{
			Double.parseDouble(d);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	//tinh cosine giua 2 so
	@Override public double cosineDouble(String s1, String s2){
	    return (Double.parseDouble(s1)==Double.parseDouble(s2))?1:0;
    }

	Pattern p = Pattern.compile("\\d+(\\.*\\d+)?");

	//tach  double va word
	@Override public String[] splitStr(String s){
		String[] arr = s.split("\\s*[^a-zA-Z]+\\s*");
		ArrayList<String> str = new ArrayList<String>(Arrays.asList(arr));
		Matcher m = this.p.matcher(s);
		while(m.find()) {
			str.add(m.group(0));
		}
		arr = str.toArray(new String[str.size()]);
		return arr;
	}

	//tinh do tg dong giua ques-ans
    @Override
    public double cosineQuesAns(String ques, String ans) throws UnknownWordException {
        double sumMax = 0;
        String[] q = this.splitStr(ques.toLowerCase());
        String[] a = this.splitStr(ans.toLowerCase());
        int len = a.length;
        for (String aj : a) {
            double max = 0;
            if(this.checkValidDouble(aj)) {
//                System.out.println("double " + aj);
                for (String qi : q)
                    max = (this.checkValidDouble(qi) && max < this.cosineDouble(aj, qi))
                            ? this.cosineDouble(aj, qi) : max;
//                {
//                    if(this.checkValidDouble(qi))
//                        System.out.println(aj+"-" +qi);
//                }
            }
            else if (this.contains(aj)) {
                for (String qi : q) {
                    if (!this.checkValidDouble(qi) && this.contains(qi)) {
                        double d = this.cosineDistance(aj, qi);
//                        System.out.println(aj + "-" + qi + "=" + d);
                        max = (max < d) ? d : max;
                    }
                }
            }
            else {
                len -= 1;
//                System.out.println(aj + "-");
            }
//            System.out.println(max);
            sumMax += max;
        }
        return sumMax / len;
    }

    @Override public double cosDisSentences(String s1, String s2) throws UnknownWordException {
		return (cosineQuesAns(s1,s2)+cosineQuesAns(s2,s1))/2;
	}

    static String[] regex = {"<br>","<br />","(?i)let's","(?i)\\s*'s\\s+","(?i)\\s*'m\\s+","(?i)\\s*'re\\s+",
            "(?i)n\\s*'t\\s+", "(?i)\\s*'ve\\s+","(?i)\\s*'ll\\s+"};

    static String[] strReplace = {"", "", "let us", " is ", " am ", " are ", " not ", " have ", " will "};

	@Override public String replaceS(String s) {
		for (int i = 0; i < regex.length; i++)
			s = s.replaceAll(regex[i], strReplace[i]);
		return s;
	}
}

