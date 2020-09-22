

-- Query posts within the last 24 HOURS
SELECT * FROM posts
  WHERE date_posted BETWEEN NOW() - INTERVAL '24 HOURS' AND NOW()

-- Query posts grouped by topic
SELECT topic FROM posts
GROUP BY topic

-- Query anonymous posts from last week order by date
SELECT * FROM posts
WHERE is_anonymous = true AND date_posted BETWEEN NOW() - INTERVAL '168 HOURS' AND NOW()
ORDER BY date_posted DESC

-- Query all the posts that have the word 'Love' in the title
SELECT * FROM posts
WHERE title LIKE '%LOVE%'
