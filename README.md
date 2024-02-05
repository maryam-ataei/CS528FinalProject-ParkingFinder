# Smart Parking Finder Mobile Application

## Introduction

The Smart Parking Finder Application is designed to help students and staff at Worcester Polytechnic Institute locate available parking spaces efficiently. Utilizing real-time data and image processing, it provides a user-friendly interface to navigate the often challenging task of parking on campus.


## Features

- Real-time parking space availability
- User-friendly navigation interface
- Database management for user data
- Analytical reports on parking usage and trends

## Prerequisites

Before running the application, ensure you have the following installed:
- Python 3.8 or higher
- For proper access to Firebase a working internet connection is needed
- Camera should be set up ... 


## Installation

To set up the application on your local system, follow these steps:
### Android (User-side)
+ Clone the repository to your local machine:
    
      git clone https://github.com/your-username/wpi-parking-finder.git
### Python (Server-side)
for server-side video processing, you need to install openCV2, python 3.10, and ultralytics packages. 

## File Summaries

### History File Detail
- `HistoryGraph Class`  provides a detailed overview of past parking availability trends, allowing users to visualize data and plan parking decisions effectively. The information includes hourly parking space counts, offering valuable insights into the patterns of parking availability over time.

### Geofencing Files Detail(inside Geofence directory)
- `GeofenceBroadcastReceiver` manages geofence triggers, fetches parking data from Firebase upon entry to a specific area, and notifies users about available parking spaces nearby through notifications.
- `GeofenceHelper` class aids in setting up geofencing functionalities. It constructs geofencing requests, defines geofences based on location and parameters, creates a pending intent for geofence events.
- `GeofenceService ` is a background Service managing geofencing operations. It sets up geofences based on specified parameters, requests location permissions if needed, and adds geofences using the GeofencingClient.
- `NavigationView` class handles map-related features:
  + Initializes Places API and sets up the map.
  + Marks locations, retrieves user location, and draws route polylines.
  + Manages permissions for location services and geofence additions based on user interactions.
### Profile File Detail
- `UserProfileFragment Fragment`To learn more about our team members, navigate to the 'About Us' section within the application. Here, you'll find detailed information about each team member.
### About US File Detail
- `MoreFragment Fragment`Explore personalized features by accessing the 'Profile' menu. Here, users can view their profile information which includes personal and car details.

### Server-side files (python)
- `mainSlotFinder.py`: This Python script serves as the server-side component of our Mobile Project. Its primary function involves detecting parking slots and updating a connected database with the results obtained. If a camera isn't accessible, the provided video file (unity_test.mov) is available for testing purposes. The core functionality revolves around utilizing various yolov8 models designed explicitly for detecting cars within the parking lot. To run the application, execute this code, ensuring the necessary dependencies are installed and the required video source is available for processing.

- `parkingSlotRect.py:` This code facilitates the extraction of parking slot areas from a video source. It prompts the user to select four points for each parking slot by interacting with the video interface. Upon selection, the coordinates of these points are displayed in the command window. These coordinates are subsequently used in the main code to define parking slots (e.g., areaB1). Once all desired parking lots have been defined by selecting four points for each rectangular region, the user can exit the program by pressing the 'q' key, finalizing the selection process.

- `dbManager.py`: this file is used to manage the database and creating the table in firebase for parking slots
