package springbootstarter.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@RestController
public class HelloController {
//    @RequestMapping("/")
    @GetMapping("/")
//    @PermitAll
    public String hello() {
        return "<h2>Welcome!</h2>";
    }

    @RequestMapping("/user")
//    @RolesAllowed({"USER","ADMIN"})
    public String user() {
        return "<h2>Welcome User!</h2>";
    }
    @RequestMapping("/admin")
//    @RolesAllowed("ADMIN")
    public String admin() {
        return "<h2>Welcome Admin!</h2>";
    }
}
