# What this is
This is a simple sample android application to show how to use the [udiwrapper](https://github.com/bensmith41/udiwrapper) library. It is only meant to show a small amount of what is possible with the wrapper.

# Building
- Clone this repo:
- Open the project in Android Studio
- Add your API Key to /app/src/main/res/values/strings.xml
    - Go to [The FDA's openFDA API Website](https://open.fda.gov/device/udi/)
    - Find the section titled "Get your API key" and request your API Key
    - Enter the API Key that's sent to your email into /app/src/main/res/values/strings.xml
- Run the app

# What it can do
- Search for a device (Unique Devices aren't supported by this app)
    - Choose the property you'd like to search for from the drop down
    - Enter the search
- Displays 10 devices at a time. As you scroll down, it adds the next ten.
- Display the name and description of the device, and whether or not the device has an expiration date, or is required to be sterilized before use.
- Save the device locally
- Display a list of saved devices on the home screen