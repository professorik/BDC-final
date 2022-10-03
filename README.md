# Getting Started

### Running

To run the service use:

./gradlew build -x test

docker-compose up -d

### Endpoints

The service has 4 endpoints:

* ###POST /api/people

Creates new user with topics.

1)`{
"id": "Hermione",
"topics": ["magic", "books", "potions"]
} `
. Creates new user with name `"Hermione"` and creates topics
`["magic", "books", "potions"]`.

2)`{
"id": "Harry",
"topics": ["magic", "quidditch", "potions"]
}`
. Creates new user with name `"Harry"` and creates topic `"quidditch"`.

In case of creating user with name that already exists **422** will be returned

* ###POST /api/people/{Name}/trust_connections

Adds or updates new connection between users.
In case of name equivalence or user absence **422** will be returned.

1)`/api/people/Harry/trust_connections
{
"Hermione": 10
}`
. Creates new connection between from Harry to Hermione with level 10.
By the way, if level is out of range `[1-10]` **422** will be returned.

* ###POST /api/path

Find the shortest path to deliver a message.
This method is implemented with Breadth-first search
to find the shortest path automatically. 

1)`
{
"text": "Voldemort is alive!",
"topics": ["magic", "potions"],
"from_person_id": "Harry",
"min_trust_level": 5
}`
. Finds the shortest path from Harry to person with topics `["magic", "potions"]`
where each of the path edges has at least `5` level of trust.

* ###POST /api/messages?bonus=true

Sends a message from user to all appropriate users.
1) If bonus is true message is sent through intermediate 
users with enough trust level, but without all-needed topics.
2) Otherwise, message is sent only to users with appropriate topics
and enough level of trust.

This method is implemented with Depth First Search.
Bonus feature requires storing not only current vertex but source.

_Input data the same as in the previous one (/api/path)._
