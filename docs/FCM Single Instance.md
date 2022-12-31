# Standard Environment
## Single instance
As the Gateway will work with your instance of Firebase, it will require you to have a credential file present.<br>
It is required that you have defined and assigned the variable `GOOGLE_APPLICATION_CREDENTIALS` before attempting to start the Gateway. <br>
For single instance of Gateway, you only need to provide the `service-account-file.json`
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