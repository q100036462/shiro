package com.boot.shiro.realm;

import com.boot.shiro.entity.User;
import com.boot.shiro.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 1.通过AuthenticationToken获取用户名
		String username = (String) token.getPrincipal();
		// 2.通过用户名查询数据库，判断该用户是否存在
		// selectOne(Admin admin); 如果admin只传递了�?个参数就根据该参数进行查询，如果传�?�两个参数就where
		User user = new User();
		user.setUsername(username);
		User u = userMapper.selectOne(user);
		// SimpleAuthenticationInfo:
		// 第一个参数:可以传Admin，也可以传username
		// 第二个参数:从数据库中查询出的密�?
		// 第三个参数:盐值 ByteSource.util.bytes("");
		// 第四个参数:当前ShiroRealm对象，为了获取用户输入的密码
		if (!"".equals(u.getUsername())){
			// 数据库中有该用户信息
			// 3.查看该用户的密码是否可以匹配的上
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(u.getUsername(), u.getPassword(), getName());
			if (u.getType()==1) {
				return info;
			}else {
				// 用户被锁定
				throw new UnknownAccountException("用户被锁定！");
			}
		}else {
			// 没有用户信息
			throw new UnknownAccountException("用户不存在！");
		}
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		return null;
	}

	public static void main(String[] args) {
		// e10adc3949ba59abbe56e057f20f883e加密一次后的123456的密码
		// fc1709d0a95a6be30bc5926fdb7f22f4加密1024次后的123456的密码
		// ee74a75f182c46effa1a4b350d537566加完盐值(ByteSource.Util.bytes("1"))后的密码

		// shiro会通过SimpleHash这个方法对密码进行加密操作
		// 第一个参数：加密的方式MD5
		// 第二个参数：密码的明文
		// 第三个参数：盐值
		// 第四个参数：加密次数
		Object pwd = new SimpleHash("MD5", "123456", ByteSource.Util.bytes("123"), 1024);
		System.out.println(pwd);
	}

}
