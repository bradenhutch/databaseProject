#!/usr/bin/env bash

userId="$1"
productId="$2"
reviewText="$3"

#add review
createReview="curl -X POST 'http://localhost:9200/reviews/_doc?pretty&pretty' -H 'Content-Type: application/json' -d '{\"product_review\": { \"product_id\": \"$2\",\"user_id\": \"$1\",\"review_text\": \"$3\"}}'"

eval $createReview