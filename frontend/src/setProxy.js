const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  const apiUrl=process.env.REACT_APP_API_URL

  app.use(
    '/api',
    createProxyMiddleware({
      target: `${apiUrl}`,
      changeOrigin: true,
    })
  );
};