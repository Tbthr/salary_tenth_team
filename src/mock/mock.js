var Mock = require('../../node_modules/mockjs')

Mock.mock('/login', 'post', require('./json/login.json'))
Mock.mock('/forget', 'post', require('./json/forget.json'))
Mock.mock('/menu', 'get', require('./json/menu.json'))
