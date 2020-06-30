**SpringMVC 统一异常处理**

###### 参数校验异常处理

~~~java
@Slf4j
@RestControllerAdvice(basePackages = "com.woniu.cloudpro.product.controller")
public class CloudproExceptionControllerAdvice {

    /**
     * 数据校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handVaildException(MethodArgumentNotValidException e) {
        log.error("数据校验出现异常", e.getMessage(), e.getCause());
        Map<String, String> errorMap = new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(400, "提交信息不合法").put("data", errorMap);
    }
}
~~~

