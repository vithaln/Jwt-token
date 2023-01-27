package com.vitu.test.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.utility.nullability.MaybeNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String userId;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String city;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Role> roles=new HashSet<>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

Set<SimpleGrantedAuthority> auth = roles.stream().map(role-> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
		return auth;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
