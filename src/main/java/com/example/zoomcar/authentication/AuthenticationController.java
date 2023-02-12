package com.example.zoomcar.authentication;
import java.util.HashMap;
import java.util.Map;

//import com.example.zoomcar.authentication.AuthenticationModel;
//import com.example.zoomcar.authentication.AuthenticationService;
import com.example.zoomcar.shared.classes.RespBody;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;
    FirebaseAuthenticationService firebaseAuthenticationService;
    private AuthenticationModel authData;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, FirebaseAuthenticationService firebaseAuthenticationService) {
        this.authenticationService = authenticationService;
        this.firebaseAuthenticationService = firebaseAuthenticationService;
    }

    @PostMapping(path = "verifyToken")
    public RespBody verifyIdToken(@RequestBody AuthenticationModel authData) throws FirebaseAuthException {
        System.out.println(authData);
        this.authData = authData;

        //verifyingIdToken
        boolean isIdTokenValid = this.firebaseAuthenticationService.verifyToken(this.authData);
        Map<String, Object> respMap = new HashMap<String, Object>();
        if(isIdTokenValid){
            //creating map with token
            respMap.put("token","authentication success");
        }
        else{
            //creating map with token
            respMap.put("token","authentication failed");
        }

        //sending json in response
        RespBody response = new RespBody("0","success",respMap);
        return response;
    }

    @PostMapping(path = "getToken")
    public RespBody getToken(@RequestBody AuthenticationModel authData) throws FirebaseAuthException {
        System.out.println(authData);
        this.authData = authData;

        //generating token from verifyId
        String token = this.firebaseAuthenticationService.generateToken(this.authData);

        //creating map with token
        Map<String, Object> respMap = new HashMap<String, Object>();
        respMap.put("token",token);

        //sending json in response
        RespBody response = new RespBody("0","success",respMap);
        return response;
    }

//    @PostMapping(path = "getToken")
//    public RespBody getToken(@RequestBody AuthenticationModel authData){
//        System.out.println(authData);
//        this.authData = authData;
//
//        //generating token from uuid
//        String token = this.authenticationService.generateToken(authData);
//
//        //creating map with token
//        Map<String, Object> respMap = new HashMap<String, Object>();
//        respMap.put("token",token);
//
//        //sending json in response
//        RespBody response = new RespBody("0","success",respMap);
//        return response;
//    }
//
//    @GetMapping(path = "validateToken")
//    public RespBody validateToken(@RequestParam String token){
//        Boolean isValid = this.authenticationService.validateToken(token,this.authData);
//
//        Map<String, Object> respMap = new HashMap<String, Object>();
//        respMap.put("isValid",isValid);
//        RespBody response = new RespBody("0","success",respMap);
//        return response;
//    }
}
