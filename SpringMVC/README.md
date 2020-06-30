**SpringMVC** 

重定向时，需要携带数据解决

~~~java
@PostMapping("/log")
public String reg(@Valid UserRegVo vo, RedirectAttributes redirectAttributes){
    // 重定向携带数据，利用session原理，将数据放在session中，
    // 只要跳到写一个页面取出数据后，session里面的数据就会删掉
    
    // 分布式下 需要解决 session共享问题
    // 重定向路径也需要重定向到完整域名下的路径（http://xxx.xxx.com/reg），不能只是"redirect:/reg"，该路径会重定向到当前项目地址，会出现问题
    redirectAttributes.addFlashAttribute("info", "info");
    
    // 将数据自动拼接在url后
    redirectAttributes.addAttribute("xx","xxx");
    return "redirect:/reg";
}
~~~
