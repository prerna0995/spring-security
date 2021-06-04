package springbootstarter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springbootstarter.model.AuthenticationRequest;
import springbootstarter.model.AuthenticationResponse;
import springbootstarter.service.MyUserDetailsService;
import springbootstarter.util.JwtUtil;

@RestController
public class HelloController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String hello() {
        return "<h2>Hello World!</h2>";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Username Password");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }
}
