微信登录

  需要  
    # 微信开放平台 appid
    wx.open.app_id=wxed9954c01bb89b47
    # 微信开放平台 appsecret
    wx.open.app_secret=a7482517235173ddb4083788de60b90e
    # 微信开放平台 重定向url
    wx.open.redirect_url=http://guli.shop/api/ucenter/wx/callback


步骤：
  1. 通过微信开发平台提供的信息获取微信登录二维码
  2. 手机确认后， 调用回调函数；
  3. 回调函数获取 code；
  4. 通过 code 值，请求微信固定的地址，得到两个值 accsess_token 和 openid
       注： openid 为每个微信用户唯一标识
  5. 查询用户是否注册过，若没有，则执行6、7 步骤
  6. 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
  7. 根据获取信息，注册用户
  8. 返回 使用 jwt 根据用户信息生成的 token 字符串
