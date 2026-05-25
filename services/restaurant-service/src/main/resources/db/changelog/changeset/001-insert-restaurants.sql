-- liquibase formatted sql

-- changeset admin:1
INSERT INTO restaurants (restaurant_id, owner_id, name, description, phone, lat, lng, status, opens_at, closes_at) VALUES
(1, 1, 'Phở 24', 'Phở bò truyền thống, nước dùng đậm đà từ xương bò ninh kỹ', '0901234567', 10.8231, 106.6297, 'active', '06:00', '22:00'),
(2, 1, 'Bún Bò Huế 3A', 'Bún bò Huế cay nồng, đậm chất cố đô', '0902345678', 10.7769, 106.7008, 'active', '06:30', '21:30'),
(3, 1, 'Cơm Tấm Bụi Sài Gòn', 'Cơm tấm sườn bì chén đặc sản Sài Gòn', '0903456789', 10.7685, 106.6934, 'active', '07:00', '22:30'),
(4, 1, 'Bánh Mì 37', 'Bánh mì nóng giòn, nhân phong phú kiểu Việt Nam', '0904567890', 10.7715, 106.6956, 'active', '05:30', '21:00'),
(5, 1, 'Lẩu Cua Đồng', 'Lẩu cua đồng đồng quê, rau đậu bắp tươi', '0905678901', 10.7885, 106.6897, 'active', '10:00', '23:00'),
(6, 1, 'Ốc Đào', 'Ốc hấp, ốc xào me, hải sản tươi sống', '0906789012', 10.7912, 106.6985, 'active', '09:00', '23:30'),
(7, 1, 'Chả Cá Lã Vọng', 'Chả cá thì là thơm lừng, bún chấm mắm tôm', '0907890123', 10.7753, 106.7012, 'active', '10:00', '22:00'),
(8, 1, 'Bánh Xèo 46', 'Bánh xèo giòn rụm, tôm thịt giá đỗ', '0908901234', 10.7701, 106.6923, 'active', '06:30', '21:30'),
(9, 1, 'Gỏi Cuốn Tôm Thịt', 'Gỏi cuốn tươi mát, bánh tráng mỏng', '0909012345', 10.7821, 106.6889, 'active', '08:00', '21:00'),
(10, 1, 'Chè Khúc Bạch', 'Chè khúc bạch hạt sen, thạch dừa thanh mát', '0910123456', 10.7745, 106.7025, 'active', '07:00', '22:00');
