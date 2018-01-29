package com.tooklili.app.web.controller.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.service.biz.intf.test.TestService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 测试控制器
 * @author shuai.ding
 * @date 2017年5月26日下午5:45:25
 */
@ApiIgnore
@Controller
@RequestMapping("/test")
public class TestController {
	
	@Resource
	private TestService testService;

	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		return "test";
	}
	
	@RequestMapping("toView")
	public String toView(){
		return "view";
	}
	
	/**
	 * 默认 springmvc 容器不支持事务，需要在配置文件中加<tx:annotation-driven/>
	 * 控制层加事务
	 * http://blog.csdn.net/mmm333zzz/article/details/45288061
	 * 
	 * <tx:annotation-driven/> only looks for @Transactional on beans in the same application context it is defined in. 
	 * This means that, if you put <tx:annotation-driven/> in a WebApplicationContext for a DispatcherServlet, 
	 * it only checks for @Transactional beans in your controllers, and not your services. 
	 * <tx:annoation-driven/>只会查找和它在相同的应用上下文件中定义的bean上面的@Transactional注解，
	 * 如果你把它放在Dispatcher的应用上下文中，
	 * 它只检查控制器上的@Transactional注解，而不是你services上的@Transactional注解。
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping("/testTransaction")
	@Transactional
	public String testTransaction(){
		testService.insertUser();
		throw new RuntimeException("insert user Exception");
	}
	
	/**
	 * service加事务
	 * @author shuai.ding
	 */
	@RequestMapping("/testTransaction2")
	public void testTransaction2(){
		testService.testTransaction();
	}
}
