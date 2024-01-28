# youtube-video-api

## Introduction
This Spring Boot application fetches the latest videos sorted in reverse chronological order of their publishing
date-time from YouTube and returns the video data for a given tag/search query in a paginated response.

## Prerequisites
Before running the application, ensure you have the following prerequisites installed:
- Java Development Kit (JDK) 17
- Docker
- Gradle

## Getting Started
To get started with the application, follow these steps:
1. Clone the repository to your local machine:
    ```bash
    git clone https://github.com/ktiwari598/youtube-video-api/
    ```
2. Navigate to the project directory:
    ```bash
    cd <project_directory>
    ```
3. Run the following command to build the application:
    ```bash
    ./gradlew clean build
    ```

## Docker Setup
The application can be run using Docker containers for easy deployment and management.

To start the Docker containers, follow these steps:
1. Ensure Docker is installed and running on your system.
2. Open a terminal window.
3. Navigate to the root directory of the project.
4. Run the following command:
    ```bash
    docker-compose up
    ```

## API Key Configuration
After running the application, API key needs to be added to the api_key_details table. You can obtain the
API key from https://developers.google.com/youtube/v3/getting-started.

To add the API key, follow these steps:
- curl --location 'http://localhost:8073/apikey/addNewKeys' \
  --header 'Content-Type: application/json' \
  --data '["replace_with_your_api_key"]'

After apiKey is added, now server will automatically fetch latest videos from youtube and save it to db every 20s.

## Testing The Application
Test the application using below APIs

curl --location 'http://localhost:8073/youtube/listVideos?page=your_page_number&size=your_page_size'

default page is 1 and size is 20

curl --location 'http://localhost:8073/youtube/searchVideos?title=your_title&description=your_description'