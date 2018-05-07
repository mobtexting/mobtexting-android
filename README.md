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
#### Usage (send SMS)

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
        mobtexting.sendSMS("This is a test","7488******",this);
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

#### Usage (how to verify OTP)
**Step 1.** Generate six digit random OTP in MainActivity.class.
```
public class MainActivity extends AppCompatActivity implements MobtextingInterface{
    private Mobtexting mobtexting;
    private String otp_sixdigit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Generate six digit OTP
        otp_sixdigit=String.valueOf(generateSixDigitRandomNumber());

        mobtexting=new Mobtexting(this);
        mobtexting.sendSMS("This is a test "+otp_sixdigit,"7488792140",this);
    }

    //Generate 6 digit number
    private int generateSixDigitRandomNumber(){
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n;
    }

    @Override
    public void onResponse(ServerResponse serverResponse) {
        Log.d("response",serverResponse.getStatus()+"  "+serverResponse.getDescription()+"  "+serverResponse.getSmsId());

        //pass the 6 digit OTP to OTPActivity class
        Intent intent=new Intent(getBaseContext(),OTPActivity.class);
        intent.putExtra("otp",otp_sixdigit);
        startActivity(intent);
    }

    @Override
    public void onError(ModelError modelError) {
        Log.d("response",modelError.getStatus()+"  "+modelError.getDescription());
    }
}

```
**Step 2.** After successful response, pass the generated OTP to OTPActivity.class.

```
public class OTPActivity extends AppCompatActivity {
    private EditText otpEditText;
    private Button btnVerify;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEditText = (EditText) findViewById(R.id.otpEditText);
        btnVerify = (Button) findViewById(R.id.btnVerify);

        //receive the 6 digit generated OTP from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            otp = extras.getString("otp");
        }

        //button click listener
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check the OTP verification
                if ((otp != null) && (otp.equals(otpEditText.getText().toString().trim()))) {
                    Log.d("verified","OTP verified successfully.");
                }else{
                    Log.d("verified","OTP verified not successfully");
                }
            }
        });
    }
}
```
#### Note: _Add Internet persmission in android mainfest file_
```
<uses-permission android:name="android.permission.INTERNET" />
```

