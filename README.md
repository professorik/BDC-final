# Getting Started

### Running

To run the service use:

* `./gradlew build -x test`

* `docker-compose up -d`

* ###POST /api/image-input

Processes the image and returns coords


params:
{
“min_level”: 0-100,
“image”: “...” //  png image as base64 DataURI string
}

Process the image and return response with code 200 and body:
{
“mines”: [ {
“x”: 1,
“y”: 1,
“level”: 0-100  }  ]  }

