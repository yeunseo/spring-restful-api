# 개요

### 1. 개발 환경 : Windows

### 2. 개발 툴 : STS 4

### 3. 사용 프레임워크 : Spring 5.3, Jackson

### Apache Tomcat v9 사용



------



##### web.xml

```xml
<servlet>
		<servlet-name>springDispatcherServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:/config/beans.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<!-- Map all requests to the DispatcherServlet for handling -->
	<servlet-mapping>
		<servlet-name>springDispatcherServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
```

* Front Controller 패턴 적용 - Front Controller 역할을 하는 **DispatcherServlet**이라는 클래스를 계층 맨 앞단에 놓고 서버로 들어오는 모든 요청을 받아서 처리하도록 구성

* DispatcherServlet의 매핑을 '/'로 지정하여 **JSP를 제외한** 모든 요청이 DispatcherServlet으로 가게 한다

  ➡ WAS가 제공하는 Default Servlet이 *.html, *.css 같은 요청을 처리할 수 없게 됨

  ➡ 그.래.서 아래 beans.xml에 Default Servlet Handler를 설정해줌



##### beans.xml

```xml
<!-- Component scan -->
<context:component-scan base-package="handstudio.controller" />
<!-- 어노테이션 사용 설정 -->
<mvc:annotation-driven />
<!-- dispatcher servlet 관련 설정 -->
<mvc:default-servlet-handler />
```

* `<context:component-scan base-package="handstudio.controller" />`

  * 패키지 경로를 명시해주면 해당 경로를 포함한 모든 하위 경로에 적용
  * @Controller, @Repository, @Service 등의 어노테이션을 명시한 자바 파일들을 Bean으로 등록시켜줌

* `<mvc:annotation-driven />`

  * 어노테이션 기반의 Controller 호출이나 필요한 Bean 객체 설정을 편리하게 하기 위한 것
  * 위에 component-scan과 중복되는 기능인듯?

* `<mvc:default-servlet-handler />`

  * DispatcherServlet이 처리하지 못한 요청을 DefaultServlet에게 넘겨주는 역할을 하는 핸들러

  * 요청 URL에 매핑되는 컨트롤러가 존재하지 않을 때, 404 응답 대신, DefaultServlet이 해당 요청의 URL을 처리하도록 함

  * **DispatcherServlet의 매핑을 '/'로 지정했을 때 사용**



# 1. POST

```java
@RequestMapping(value="/user", method=RequestMethod.POST,
                headers= {"Content-type=application/json"})
@ResponseBody
public Map insertUser(@RequestBody Map<String, String> list) {

    Map result = new HashMap();
    result.put("success", Boolean.TRUE);

    return result;
}
```





