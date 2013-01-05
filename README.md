#Recommender Demo
###Aim
This is a small project to show Apache Mahout working as an offline recommendation for an ecommerce website. The code included creates random data for 5000 customers based on a million random recommendations for 45000 products.  These values can be adjusted within the code if you require.

##Getting Started

###Requirements
You'll require Apache Mahout ([http://mahout.apache.org](http://mahout.apache.org)) and it's required dependencies. I've used MySQL as the database but you can adjust as you wish.

###Setup the database
Assuming you are running MySQL the schema is in the **schema** directory.

	mysqladmin -u [user] create mahout
	mysql -u [user] mahout < schema/schema.sql

###Compile the code
This demo is build 100% in Java and there's an ant script to compile the code for you (assuming that you have the required jar files in the lib directory first).

	ant compile.core
	
###Creating some data
Before we can recommend anything we need some data to work with.  The PopData class will create a millon rows of random data for you.  If you want to tweak those numbers you can do. 

##Now for the fun bit
Well, *I* think it's fun! 

These recommenders are based on the recommenders in the book [Mahout In Action](http://www.amazon.co.uk/gp/product/1935182684/ref=as_li_ss_tl?ie=UTF8&tag=jasonbelljava-21&linkCode=as2&camp=1634&creative=19450&creativeASIN=1935182684) by Sean Owen, Robin Anil, Ted Dunning and Ellen Friendman. 

**RecommenderDemo** uses a direct connection to the database and attempts to recommend three products for each user.

**RecommenderDemo2** does the same but reading from a dumped .csv file (look at the shell script importdata.sh and dumptable.sql files, they'll do the work for you).

The csv data is comma seperated as
	
	user_id, item_id, preference
	
The preference is basically a rating from 0-5. 

The recommenders will attempt to give three products a user might light that i) they don't already have and ii) is within the same interests as what the other users have bought.

Assuming you have the csv file in the data directory you can run the runrecommender.sh file and it will start ploughing through all the users and recommending things.

	13/01/05 11:02:27 INFO file.FileDataModel: Creating FileDataModel for file data/recom.csv
	13/01/05 11:02:27 INFO file.FileDataModel: Reading file info...
	13/01/05 11:02:31 INFO file.FileDataModel: Processed 1000000 lines
	13/01/05 11:02:31 INFO file.FileDataModel: Read lines: 1000000
	13/01/05 11:02:32 INFO model.GenericDataModel: Processed 5000 users
	User 2 >> RecommendedItem[item:19037, value:0.91221946]
	User 3 >> RecommendedItem[item:14475, value:2.368465]
	User 3 >> RecommendedItem[item:24401, value:0.90410054]
	User 4 >> RecommendedItem[item:1829, value:1.8210385]
	User 6 >> RecommendedItem[item:16923, value:1.65651]
	User 6 >> RecommendedItem[item:32356, value:1.176305]
	User 8 >> RecommendedItem[item:19928, value:0.874892]
	User 9 >> RecommendedItem[item:7536, value:3.5504751]
	User 10 >> RecommendedItem[item:8291, value:0.48909804]
	
	
##Taking it further	
The nice thing about this small project is that it could be used outside of the ecommerce platform.  There are a couple of things that you may want to do before deploying it anywhere though.

####Scheduling
First of all even for the most busy of ecommerce sites would rarely do this in real time. Not impossible but at the same time is there any real need to? 

1/6/12/24 hours makes sense depending on the volume of transactions.  During test trials of the code these figures used (5000 customers, 45000 items and 1,000,000 transactions) gave some meaningful recommendations. Lower the item and customer counts and the chances of results being generated were vastly reduced.

####Updating the recommendations back to the user
This system, as you can see, just dumps the recommendations back to the console. Ideally you want to be writing the recommendations in to a datastore that can be then used by the ecommerce platform again.

####Tweaking the results
The demo code will show all recommendations. Now if a customer is rating something less than 3 stars then in my opinion it's not a rating to be measuring. The last thing you want to be doing is putting negative recommendations out to your customers. 

##Feedback and thanks
Feel free to leave comments as you wish. This was sometihng put together rather quickly, it's certainly not production ready yet. It was to scratch an itch more than anything. 

For more thoughts on this sort of thing I blog at [Data is Sexy](http://www.dataissexy.co.uk)


