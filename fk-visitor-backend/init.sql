	CREATE DATABASE baseproject;
	CREATE USER 'base-user'@'%' IDENTIFIED BY '!@#456Poiuy';
	GRANT ALL ON baseproject.* TO 'base-user'@'%';
	FLUSH PRIVILEGES;
    
	USE baseproject;
	INSERT INTO `t_man_menu` (id, alias, code, icon, name, parent_code, url) VALUES (1,'root-system','70','im im-file-text','系统管理',NULL,''),(11,'sub-operator','7001','','用户管理','70','system/operator/operator.html'),(12,'sub-role','7002','','角色管理','70','system/operatorrole/operator_role.html'),(13,'sub-menu','7003','','菜单管理','70','system/menu/menu.html');
	INSERT INTO `t_man_role` (id) VALUES ('admin');
	INSERT INTO `t_man_role_menu_map` (role_id, menu_id) VALUES ('admin',1),('admin',11),('admin',12),('admin',13);
	INSERT INTO `t_operator` (id, create_at, password, username, name) VALUES ('firstborn',NULL,'{plain}111111','admin','初始管理员');
	INSERT INTO `t_operator_role` (id, is_supervisor, key_code, name) VALUES (1,true,'admin','平台管理员');
	INSERT INTO `t_operator_role_map` (operator_id, role_id) VALUES ('firstborn',1);
	COMMIT;