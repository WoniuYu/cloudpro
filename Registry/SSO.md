**单点登录**



给登录服务浏览器保存uuid

~~~java
public String doLogin(@RequestParam("username") String username,
					  @RequestParam("password") String password,
                      @RequestParam("url") String url,
                      HttpServletResponse response) {
    if(login(username, password)){
        // 登录成功
        // 缓存uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue(uuid, username);
        Cookue sso_token = new Cookie("sso_token");
        response.addCookie(sso_token);
        return "redirect:" + url + "?token=" +  uuid;
    }
    
    // 登录失败
    return "login";
    
}
~~~

