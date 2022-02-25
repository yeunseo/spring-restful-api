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
<context:component-scan base-package="restapi.controller" />
<!-- 어노테이션 사용 설정 -->
<mvc:annotation-driven />
<!-- dispatcher servlet 관련 설정 -->
<mvc:default-servlet-handler />
```

* `<context:component-scan base-package="restapi.controller" />`

  * 패키지 경로를 명시해주면 해당 경로를 포함한 모든 하위 경로에 적용
  * @Controller, @Repository, @Service 등의 어노테이션을 명시한 자바 파일들을 Bean으로 등록시켜줌

* `<mvc:annotation-driven />`

  * 어노테이션 기반의 Controller 호출이나 필요한 Bean 객체 설정을 편리하게 하기 위한 것
  * 위에 component-scan과 중복되는 기능인듯?

* `<mvc:default-servlet-handler />`

  * DispatcherServlet이 처리하지 못한 요청을 DefaultServlet에게 넘겨주는 역할을 하는 핸들러

  * 요청 URL에 매핑되는 컨트롤러가 존재하지 않을 때, 404 응답 대신, DefaultServlet이 해당 요청의 URL을 처리하도록 함

  * **DispatcherServlet의 매핑을 '/'로 지정했을 때 사용**



------



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

* `@RequestMapping`
  * 메서드와 URI를 매핑해주는 역할
  * headers(또는 produces) 옵션으로 response의 content-type을 json으로 설정

* `@ResponseBody`

  * Java 객체를 JSON 포맷으로 변환

    Java 객체를 HTTP 응답 body로 전송

* `@RequestBody Map<String, String> list`
  * 클라이언트 요청으로부터 들어오는 JSON 객체를 Java 객체로 변환
  * 변환한 객체를 'list'라는 이름의 Map 객체에 저장
  * 잘 넘어왔나 확인은 `list.get("loginId")` 이용
* 응답으로 보낼 Map 객체인 'result' 생성하여 보낼 내용 저장 후 리턴





# 2. POST

```java
@RequestMapping(value="/login", method=RequestMethod.POST,
                headers= {"Content-type=application/json"})
@ResponseBody
public Map login(@RequestBody Map<String, String> list) {

    String token = "123456";

    Map result = new HashMap();
    result.put("token", token);
    result.put("success", true);

    return result;
}
```

* 위와 같음



# 3. GET

```java
@RequestMapping(value="/user", method= RequestMethod.GET)
@ResponseBody
public Map getUser(@RequestParam("token") int token) {

    String id = "";
    String nickname = "";

    if(token == 123456) {
        id = "hello";
        nickname = "restapi";
    }

    Map result = new HashMap();
    result.put("loginId", id);
    result.put("nickname", nickname);

    return result;
}
```

* `http://localhost:8000/user?token=123456`
  * 쿼리스트링 `user?token=123456`을 통해 'token'의 값을 받아와야 함을 알 수 있음
* `@RequestParam`을 통해 'token'의 값을 가져와 int 타입의 'token' 변수에 저장한다
* 토큰이 123456이면 id와 nickname 저장하여 반환



------



# 회고

1. 초반에 프로젝트 세팅이 힘들었음(web.xml과 beans.xml)

2. Boolean값이 안넘어와서 당황했음

   * 초반에 클래스의 리턴 형식을 제네릭을 잘못 이해한채 이용했기 때문 `Map<String, String>`
   * String은 Boolean이 아니기 때문에 값이 안넘어왔음
   * 제너릭을 없애니 잘 넘어왔음

3. @RequestParam과 @PathVariable 혼동

   * **@PathVariable**은 URI가 'localhost:8000/user/123456' 일 때 사용

   * **@RequestParam**은 URI가 'localhost:8000/user?token=123456'일 때 사용

     ➡ 쿼리 스트링으로 표현 되었을 때 사용



# REST란

* Representational State Transfer

  일반적으로 JSON이나 XML을 통해 데이터를 주고받음

* 네트워크 상  Client-Server사이 통신방식 중 하나

* HTTP URI를 통해 자원 명시, HTTP Method를 통해 자원에 대한 CRUD기능 적용



#### REST가 필요한 이유

* 최근 서버프로그램은 다양한 클라이언트(브라우저, 안드로이드, 아이폰 등)와 통신을 할 수 있어야 하기 때문

  ➡ 멀티플랫폼 지원을 위함, 성능보다는 **"호환성"**이 포인트!



#### RESTful 이란

* REST 아키텍처를 구현하는 웹 서비스를 나타내기 위해 사용되는 용어
* REST API를 제공하는 웹 서비스 ➡ RESTful 하다

