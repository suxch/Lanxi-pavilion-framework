# suxch-vue2

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

vue.js 笔记

修改npm源
```
npm config set registry https://registry.npmmirror.com
```
安装vue@cli
npm i @vue/cli -g

创建vue项目
通过cmd在指定目录中进行创建，否则默认创建在用户文件夹下
vue create projectName

运行vue项目
npm run serve(默认是serve，如果需要更改，需要去package.json文件中进行修改)

编译vue项目
npm run build(同运行原理相同)

下载指定依赖，比如说less
npm install -g less
npm install -g less-loader
安装指定依赖到项目中去
npm i less
npm i less-loader

在main.js中引用框架的时候，需要注意
先引入框架 再引入模块，否则模块中修改框架中的样式会被忽略。按照先框架后模块的方式引入，模块中的样式的优先级会更高（需要待详细验证）

拉取vue项目后，在vue项目更目录执行指令
npm cache clean -force 清除npm缓存
npm install 重新安装依赖

bootstrap和bootstrap-vue的下载指令
npm install bootstrap
npm install bootstrap@5.3.0-alpha1
npm install bootstrap-vue

安装vue版的ajax-axios
npm install axios
npm i axios