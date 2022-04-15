package innowise.zuevsky.helpdesk.api;

import innowise.zuevsky.helpdesk.dto.AuthenticationDto;
import innowise.zuevsky.helpdesk.response.AuthenticationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Authentication")
@Controller
public interface IAuthenticationController {

    @ApiOperation(value = "Login")
    AuthenticationResponse authenticate(@RequestBody AuthenticationDto request);

    @ApiOperation(value = "Logout")
    void logout(HttpServletRequest request, HttpServletResponse response);

}