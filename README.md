# cloud_capstone

Github repo https://github.com/soinegve/cloud_capstone

Implemented microblogging backend in Java

Lambdas
- Authorizer : using auth0
- Follow : Api to follow or unfollow a user
- Post : a user posts a text plus a reference to a picture ( pre-signed url ) 
- GetPresignedUrl : obtain a pre signen url to save the picture
- GetFeeds : get feeds of users followed



Collections
- The first collections creates 5 posts from 3 different users, each post has a comment and uploads a photo
- The second collection shows how one user ( Rachel ) can follow and unfollow the other two and how this affects her feeds 

To Run collection install newman 

https://learning.postman.com/docs/running-collections/using-newman-cli/command-line-integration-with-newman/

npm install -g newman

Then from files directory run

newman run "Post by user.postman_collection.json" -d data.json

to create posts followed by 

newman run "User Rachel follows - unfollows and her feeds change\!.postman_collection.json"

the name of each request in this collection describes the expected behaviour