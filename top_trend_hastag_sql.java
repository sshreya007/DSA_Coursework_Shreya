// -- Step 1: Check if tweets from February 2024 are correctly filtered
// SELECT * FROM Tweets WHERE tweet_date BETWEEN '2024-02-01' AND '2024-02-29';

// -- Step 2: Extract words from tweets to see if they are split correctly
// SELECT tweet, regexp_split_to_table(tweet, '\s+') AS word
// FROM Tweets
// WHERE tweet_date BETWEEN '2024-02-01' AND '2024-02-29';

// -- Step 3: Filter only hashtags to check if filtering is working
// SELECT word AS hashtag
// FROM (
//     SELECT regexp_split_to_table(tweet, '\s+') AS word
//     FROM Tweets
//     WHERE tweet_date BETWEEN '2024-02-01' AND '2024-02-29'
// ) words
// WHERE word LIKE '#%';

// -- Step 4: Final query to get the top 3 trending hashtags
// WITH extracted_hashtags AS (
//     SELECT LOWER(word) AS hashtag
//     FROM (
//         SELECT regexp_split_to_table(tweet, '\s+') AS word
//         FROM Tweets
//         WHERE tweet_date BETWEEN '2024-02-01' AND '2024-02-29'
//     ) words
//     WHERE word LIKE '#%'
// )
// SELECT hashtag, COUNT(*) AS count
// FROM extracted_hashtags
// GROUP BY hashtag
// ORDER BY count DESC, hashtag DESC
// LIMIT 3;