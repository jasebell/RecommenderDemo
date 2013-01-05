package uk.co.dataissexy.mahout;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class RecommenderDemo {

	public RecommenderDemo() {
		MysqlDataSource datasource = new MysqlDataSource();
		datasource.setServerName("localhost");
		datasource.setUser("root");
		datasource.setPassword("");
		datasource.setDatabaseName("mahout");

		JDBCDataModel dataModel = new MySQLJDBCDataModel(datasource, "recom",
				"user_id", "item_id", "preference", null);

		UserSimilarity similarity;
		try {
			similarity = new PearsonCorrelationSimilarity(dataModel);
			UserNeighborhood neighbourhood = new NearestNUserNeighborhood(2,
					similarity, dataModel);

			Recommender recommender = new GenericUserBasedRecommender(
					dataModel, neighbourhood, similarity);
			long start = System.currentTimeMillis();
				List<RecommendedItem> recommendations = recommender.recommend(
						1, 3);
				for (RecommendedItem recommendation : recommendations) {
					System.out.println(recommendation);
					
				}
				long stop = System.currentTimeMillis();
				System.out.println("Took: " + (stop - start) + " millis");
			
		} catch (TasteException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		RecommenderDemo r = new RecommenderDemo();
	}

}
