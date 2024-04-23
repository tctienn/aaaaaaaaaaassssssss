package ql.vn.qlsp.service;

import jdk.nashorn.internal.runtime.options.Option;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ql.vn.qlsp.dto.MessageDto;
import ql.vn.qlsp.dto.UserDto;
import ql.vn.qlsp.entity.TokenUserEntity;
import ql.vn.qlsp.entity.UserEntity;
import ql.vn.qlsp.repository.TokenUserRepository;
import ql.vn.qlsp.repository.UserRepository;
import ql.vn.qlsp.security.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GmailService gmailService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?

        UserEntity userEntity = userRepository.findByUserName(username);
//        System.out.println("fint user : " + userEntity);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(userEntity);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }


    public  String generateRandomToken() {
        byte[] randomBytes = new byte[32]; // Độ dài mã xác thực, bạn có thể điều chỉnh độ dài này
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }


    @Autowired
    private HttpServletRequest request;

    @Autowired
    TokenUserRepository tokenUserRepository;
    @Transactional
    public UserEntity singUp(UserDto userDto) throws Exception {
        if(userRepository.findByUserName(userDto.getUserName())!=null){
            throw new Exception("Tên tài khoản đã tồn tại");

        }
        if(userDto.getId()!=null){
            throw new Exception("dữ liệu không hợp lệ");

        }
        UserEntity userEntity = new UserEntity(userDto);
        userEntity.setCreateTime(Date.valueOf(LocalDate.now()));
        userEntity.setRole(null);
        UserEntity userEntity1 = userRepository.save(userEntity);
        String token = generateRandomToken();
        TokenUserEntity tokenUserEntity = new TokenUserEntity();
        tokenUserEntity.setToken(token);
        tokenUserEntity.setUserId(userEntity1.getId());
        tokenUserRepository.save(tokenUserEntity);

        String scheme = request.getScheme(); // http
        String serverName = request.getServerName(); // hostname.com
        int serverPort = request.getServerPort(); // 80

        String emailBody = scheme + "://" + serverName + ":" + serverPort + "/" + token;
        gmailService.sendEmail(userDto.getGmail(),"xác thực tài khoản",emailBody);
        return userEntity1;
    }

}
