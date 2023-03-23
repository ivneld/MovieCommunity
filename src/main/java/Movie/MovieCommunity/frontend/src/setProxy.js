const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
    // # 서버 URL or localhost:설정한포트번호
      target: 'http://localhost:8080',	
      changeOrigin: true,
    })
  );
};