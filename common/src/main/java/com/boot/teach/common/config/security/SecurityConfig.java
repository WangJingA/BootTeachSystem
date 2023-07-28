package com.boot.teach.common.config.security;


import com.boot.teach.common.exception.AccessDeniedHandlerImpl;
import com.boot.teach.common.exception.AuthenticationEntryPointImpl;
import com.boot.teach.common.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccessDeniedHandlerImpl accessDeniedHandler;


    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    private final AuthenticationEntryPointImpl entryPoint;

    public SecurityConfig(AccessDeniedHandlerImpl accessDeniedHandler,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          AuthenticationEntryPointImpl entryPoint) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.entryPoint = entryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //静态资源配置
    @Override
    public void configure(WebSecurity web) throws Exception {
        //swagger2所需要用到的静态资源，允许访问
        web.ignoring(). antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/v3/**")
                .antMatchers("/swagger-resources/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic().disable()
                //表单提交disable
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                //放行的请求配置，不需要经过验证
                .antMatchers("/**/login","/**/loginOut", "/captcha/**", "/manager/backCall",
                        //超级管理员接口不做权限拦截
                        "/super/**",
                        //请求放行swagger-ui，不需要通过过滤器链
                        "/swagger-ui/", "/swagger-ui.html/", "/*.html", "/favicon.ico",
                        "/**/*.html", "/**/*.css", "/**/*.js", "/swagger-resources/**", "/v3/api-docs/**"
                )
                .permitAll()
                .antMatchers(HttpMethod.POST,"/manager/createDep").hasAnyAuthority("DepartmentCreate.create")
                .antMatchers(HttpMethod.POST,"/manager/listDep").hasAnyAuthority("DepartmentList.list")
                .antMatchers(HttpMethod.POST,"/manager/uploadSchoolIm").hasAnyAuthority("SchoolManage.images.upload")
                .antMatchers(HttpMethod.POST,"/manager/listImages").hasAnyAuthority("SchoolManage.images.list")
                .antMatchers(HttpMethod.POST,"/manager/delImages").hasAnyAuthority("SchoolManage.images.delete")
                .antMatchers(HttpMethod.POST,"/manager/schoolDetail").hasAnyAuthority("SchoolMangeDetail.show")
                .antMatchers(HttpMethod.POST,"/manager/listMajor").hasAnyAuthority("MajorList.list")
                .antMatchers(HttpMethod.POST,"/manager/updateMajor").hasAnyAuthority("MajorList.update")
                .antMatchers(HttpMethod.POST,"/manager/delMajor").hasAnyAuthority("MajorList.del")
                .antMatchers(HttpMethod.POST,"/manager/listMajor").hasAnyAuthority("MajorList.query")
                .antMatchers(HttpMethod.POST,"/manager/addMajor").hasAnyAuthority("MajorCreate.create")
                .antMatchers(HttpMethod.POST,"/passUse/schoolList").hasAnyAuthority("PassUse.school.list")
                .antMatchers(HttpMethod.POST,"/passUse/uploadIcon").hasAnyAuthority("PassUse.file.upload")
                .antMatchers(HttpMethod.POST,"/passUse/distinctDep").hasAnyAuthority("PassUse.department.list")
                .antMatchers(HttpMethod.POST,"/passUse/classList").hasAnyAuthority("PassUse.class.list")
                .antMatchers(HttpMethod.POST,"/passUse/majorList").hasAnyAuthority("PassUse.major.list")
                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
