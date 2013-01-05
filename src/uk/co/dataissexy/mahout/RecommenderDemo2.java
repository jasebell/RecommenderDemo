package uk.co.dataissexy.mahout;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class RecommenderDemo2 {

	public RecommenderDemo2(String filepath) {

		UserSimilarity similarity;
		try {

			FileDataModel dataModel = new FileDataModel(new File(filepath));

			similarity = new PearsonCorrelationSimilarity(dataModel);
			UserNeighborhood neighbourhood = new NearestNUserNeighborhood(2,
					similarity, dataModel);

			Recommender recommender = new GenericUserBasedRecommender(
					dataModel, neighbourhood, similarity);
			long start = System.currentTimeMillis();
			for (int i = 1; i <= 5000; i++) {
				List<RecommendedItem> recommendations = recommender.recommend(
						i, 3);
				for (RecommendedItem recommendation : recommendations) {
					System.out.println("User " + i + " >> " + recommendation);
				}
			}
			long stop = System.currentTimeMillis();
			System.out.println("Took: " + (stop - start) + " millis");
		} catch (TasteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		if (args.length > 0) {
			RecommenderDemo2 r = new RecommenderDemo2(args[0]);
		} else {
			System.out.println("Usage: RecommenderDemo2 [path to csv file]");
		}
	}

}
