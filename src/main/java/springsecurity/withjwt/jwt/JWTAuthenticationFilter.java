package springsecurity.withjwt.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import springsecurity.withjwt.security.CustomUserDetail;
import springsecurity.withjwt.security.CustomUserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try{
            /** lấy jwt  tu request*/
            String jwt = getJwt(httpServletRequest);

            if (StringUtils.hasText(jwt)&& jwtTokenProvider.validateToken(jwt)){/** kiểm tra hợp tokem có hợp lệ không*/

                /** lấy id của người dùng*/
                Long userId = jwtTokenProvider.getUserIDFromJWT(jwt);

                CustomUserDetail userDetail = (CustomUserDetail) userDetailService.loadUserById(userId);
                if (userDetail!= null){

                    /** nếu người dùng hợp lệ thì set thông tin cho security context*/
                    UsernamePasswordAuthenticationToken
                            authenticationToken = new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }

        }catch (Exception ex){
            log.error("fail on set user authentication",ex);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }


    /** lấy jwt tu request gửi về*/
    public String getJwt(HttpServletRequest request){

        String bearToken = request.getHeader("Authorization");
        /** kiểm trong header co thông tin jwt không*/
        if (StringUtils.hasText(bearToken)&& bearToken.startsWith("Bearer ")){
            return bearToken.substring(7);
        }
        return null;
    }
}
