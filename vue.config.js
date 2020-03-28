const path = require('path')

module.exports = {
  pages: {
    index: {
      entry: './src/main/webapp/main.ts'
    }
  },
  chainWebpack: config => {
    config.resolve.alias.set('@', path.resolve(__dirname, 'src/main/webapp'));
  }
};
