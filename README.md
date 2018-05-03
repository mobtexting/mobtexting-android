# Send SMS Android SDK
_Integrate android sdk to Send SMS from Mobtexting_
## __Getting Started__
### Gradle
**Step 1.** Add the JitPack repository to your build file
```
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```
**Step 2.** Add the dependency  
```
dependencies {
  implementation 'com.github.mobtexting:mobtexting-android:v1.0.3'
}
```
#### define _API KEY_ and _Sender_ ID in Manifest file inside Application tag
```
  <meta-data android:name="mobtexting.api_key" android:value="@string/mobtextingapikey" />
  <meta-data android:name="mobtexting.sender_id" android:value="@string/mobtextingsenderid" />
```
#### Usage

```
public class MainActivity extends AppCompatActivity implements MobtextingInterface{
    private Mobtexting mobtexting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //create instance of Mobtexting class
        mobtexting=new Mobtexting(this);
        
        //send the SMS
        mobtexting.sendSMS("This is a test","7488792140",this);
    }

    @Override
    public void onResponse(ServerResponse serverResponse) {
        Log.d("response",serverResponse.getStatus()+"  "+serverResponse.getDescription()+"  "+serverResponse.getSmsId());
    }

    @Override
    public void onError(ModelError modelError) {
        Log.d("response",modelError.getStatus()+"  "+modelError.getDescription());
    }
}
```
