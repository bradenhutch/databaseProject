#!/usr/bin/env bash

userId="$1"
productId="$2"

for arg in $@; do
	if [ "$arg" != "$userId" ]; then
		if [ "$arg" != "$productId" ]; then
		reviewText+=" "$arg
		fi
	fi
done

createReview="curl -X POST 'http://localhost:9200/reviews/_doc?pretty&pretty' -H 'Content-Type: application/json' -d '{\"product_review\": { \"product_id\": \"$2\",\"user_id\": \"$1\",\"review_text\": \"${reviewText:1}\"}}'"

eval $createReview