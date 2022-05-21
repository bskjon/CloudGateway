# Cloud Gateway
Gateway to interact with 3rd party cloud solutions that requires a endpoint where the apps should interact for some reason.
In order to enable connectivity and functionality with Googles Firebase, a configuration file is required.

# Firebase Support
## Enable a Single-Instance Firebase app
### For non-containerized environments
This is required to be present before the Gateway is started 
<br>
<br>
Linux/Unix
```bash
export GOOGLE_APPLICATION_CREDENTIALS="/home/user/Downloads/service-account-file.json"
```
Windows
```powershell
$env:GOOGLE_APPLICATION_CREDENTIALS="C:\Users\username\Downloads\service-account-file.json"
```

### For containerized environments
Use the environment variable to set the JSON file path
Env-Variable
```text
GOOGLE_APPLICATION_CREDENTIALS
```

## Enable Multi-Instances for Firebase App
In order to support and correctly use the right instance of Firebase, ensure that you provide and configure this correctly
Create the following:
firebase-apps.json

```json
{
  "com.example.fancyappOne": "/full/fancy/path/for/firebase-app-thingy.json",
  "com.example.fancyappTwo": "/full/fancy/path/for/firebase-app-other-thingy.json"
}
```
Note! <br/>
First item will always get designated as default item
### For all environments
Environment variable
```text
FIREBASE_CREDENTIALS
```
use the same calling as single-instance but replace it with "FIREBASE_CREDENTIALS"
