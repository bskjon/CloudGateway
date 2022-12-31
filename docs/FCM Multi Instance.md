# Standard Environment
## Multi instance
As the Gateway will work with your instance of Firebase, it will require you to have a credential file present.<br>
It is required that you have defined and assigned the variable `FIREBASE_CREDENTIALS` before attempting to start the Gateway. <br>
For the gateway to function in the multi instance mode, you only need to provide a <strong>JSON</strong> file formatted like this example. <br>
In this json file, you will have to specify the package name associated with the firebase credential.

### multitenant.json
```json
{
  "com.example.one": "/home/user/auth/service-account-file-one.json",
  "com.example.two": "/home/user/auth/service-account-file-two.json"
}
```
In order to use this with Gateway, you will need to change the variable to `FIREBASE_CREDENTIALS`
<br>
<br>
Linux/Unix

```bash
export FIREBASE_CREDENTIALS="/home/user/multitenant.json"
```

Windows
```powershell
$env:FIREBASE_CREDENTIALS="C:\Users\username\multitenant.json"
```