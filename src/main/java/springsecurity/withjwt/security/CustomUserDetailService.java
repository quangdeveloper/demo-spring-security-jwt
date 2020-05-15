package springsecurity.withjwt.security;

import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springsecurity.withjwt.model.User;
import springsecurity.withjwt.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(s);

        if (user == null) throw  new UsernameNotFoundException(s);

        return new CustomUserDetail(user);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {

        User user = userRepository.findById(id).orElseThrow(
                ()->new UsernameNotFoundException(id.toString())
        );
        return new CustomUserDetail(user);
    }

}
