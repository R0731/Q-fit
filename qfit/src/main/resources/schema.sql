DROP DATABASE IF exists qfit;
CREATE DATABASE qfit;

USE qfit;

-- user 테이블 (모든 사용자 정보)
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(50) UNIQUE NOT NULL,
    user_pw VARCHAR(255) NOT NULL,
    user_name VARCHAR(100),
    phone_number VARCHAR(20),
    email VARCHAR(100),
    gender VARCHAR(10),
    birth_date DATE,
    user_img VARCHAR(255),
    user_type TINYINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_agreed boolean DEFAULT FALSE
);

-- trainer 테이블 (트레이너 정보, user와 1:多 관계)
CREATE TABLE trainer (
    id INT PRIMARY KEY,
    gym VARCHAR(100),
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);

-- trainee 테이블 (훈련생 정보, user와 1:1 관계)
CREATE TABLE trainee (
    id INT PRIMARY KEY,
    trainer_id INT,
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (trainer_id) REFERENCES trainer(id) ON DELETE SET NULL
);

-- today_quest 테이블 (훈련생이 받은 퀘스트 정보)
CREATE TABLE quest (
    quest_id INT PRIMARY KEY AUTO_INCREMENT,
    trainee_id INT,
    trainer_id INT,
    start_at DATETIME,
    end_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME,
    FOREIGN KEY (trainee_id) REFERENCES trainee(id) ON DELETE SET NULL, 
    FOREIGN KEY (trainer_id) REFERENCES trainer(id) ON DELETE SET NULL
);

CREATE TABLE exercise(
    exercise_id INT PRIMARY KEY AUTO_INCREMENT,
    exercise_type ENUM('Weight', 'Cardio'),
    exercise_parts varchar(40),
    exercise_name varchar(100)
);

-- task 테이블 (퀘스트 내 개별 작업)
CREATE TABLE task (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    quest_id INT,
    order_index INT,
    is_completed BOOLEAN DEFAULT FALSE,
    count INT,
    weight_kg INT,      -- Weight에서 사용 (무게)
    cardio_minutes INT, -- Cardio에서 사용 (시간)
    exercise_id INT,
    FOREIGN KEY (quest_id) REFERENCES quest(quest_id) ON DELETE SET NULL,
    FOREIGN KEY (exercise_id) REFERENCES exercise(exercise_id) ON DELETE SET NULL
);

-- review 테이블 (quest와 관련된 리뷰)
CREATE TABLE review (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    quest_id INT,
    trainee_id INT,
    difficulty ENUM('EASY', 'MEDIUM', 'HARD'),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quest_id) REFERENCES quest(quest_id) ON DELETE SET NULL,
    FOREIGN KEY (trainee_id) REFERENCES trainee(id) ON DELETE SET NULL
);

-- feedback 테이블 (review에 대한 트레이너의 피드백)
CREATE TABLE feedback (
    feedback_id INT PRIMARY KEY AUTO_INCREMENT,
    quest_id INT,
    trainer_id INT,
    content TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quest_id) REFERENCES quest(quest_id) ON DELETE SET NULL,
    FOREIGN KEY (trainer_id) REFERENCES trainer(id) ON DELETE SET NULL
);

-- notification 테이블 (사용자에게 발송된 알림 정보)
CREATE TABLE notification (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    message TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE SET NULL
);

INSERT INTO exercise(exercise_type, exercise_parts, exercise_name)
VALUES ('Cardio', 'cardio','런닝 머신'),
('Cardio', 'cardio','싸이클'),
('Weight', 'chest','벤치 프레스'),
('Weight', 'chest','딥스'),
('Weight', 'chest','덤벨 플라이'),
('Weight', 'chest','케이블 크로스 오버'),
('Weight', 'chest','푸시업'),
('Weight', 'back','랫풀다운'),
('Weight', 'back','친업'),
('Weight', 'back','루마니안 데드리프트'),
('Weight', 'back','풀업'),
('Weight', 'back','티바로우머신'),
('Weight', 'shoulder','오버헤드프레스'),
('Weight', 'shoulder','사이드 레터럴 레이즈'),
('Weight', 'shoulder','페이스 풀'),
('Weight', 'shoulder','숄더프레스'),
('Weight', 'shoulder','프론트레이즈'),
('Weight', 'arm','바벨컬'),
('Weight', 'arm','덤벨컬'),
('Weight', 'arm','암컬 머신'),
('Weight', 'arm','트라이셉 익스텐션머신'),
('Weight', 'arm','덤벨 리스트 컬'),
('Weight', 'leg','프론트 스쿼트'),
('Weight', 'leg','레그 프레스'),
('Weight', 'leg','레그 컬'),
('Weight', 'leg','레그 익스텐션'),
('Weight', 'leg','덤벨 런지')
;

-- user 테이블에 초기 사용자 추가
INSERT INTO user (user_id, user_pw, user_name, phone_number, email, gender, birth_date, user_img, user_type)
VALUES 
('ssafy', 'ssafy', '트레이너싸피', '010-1234-5678', 'alice@example.com', 'Female', '1990-05-15', '1732280917068_male_1.jpg', 1),
('ssafy2', 'ssafy2', '트레이니싸피', '010-1234-5678', 'bob@example.com', 'Female', '1990-05-15', '1732281796738_female_1.jpg', 2);

-- 추가 user 데이터 삽입
INSERT INTO user (user_id, user_pw, user_name, phone_number, email, gender, birth_date, user_img, user_type)
VALUES
-- Trainer 데이터
('trainer1', 'securepw123!', '김철수', '010-1234-5678', 'cheolsu.kim@fitnesspro.com', 'Male', '1985-03-22', NULL, 1),
('trainer2', 'besttrainer987!', '박영호', '010-8765-4321', 'youngho.park@fitnesspro.com', 'Male', '1987-08-10', NULL, 1),

-- Trainee 데이터
('trainee1', 'trainee2024$', '이민정', '010-5678-1234', 'minjung.lee@fitlife.com', 'Female', '1995-12-05', NULL, 2),
('trainee2', 'fitlife789!', '최성민', '010-4321-8765', 'sungmin.choi@fitlife.com', 'Male', '1999-06-18', '1732280917068_male_1.jpg', 2);

-- trainer 테이블 데이터 삽입
INSERT INTO trainer (id, gym)
SELECT id, CONCAT('Gym_', id)
FROM user
WHERE user_type = 1;

-- Trainee 테이블에 trainer_id가 2인 데이터만 추가
INSERT INTO trainee (id, trainer_id)
SELECT 
    u.id, 
    1 AS trainer_id -- 항상 trainer_id를 2로 설정
FROM 
    user u
WHERE 
    u.user_type = 2; -- Trainee인 사용자만 추가

-- Quest 테이블 하드코딩 방식으로 더미 데이터 삽입
-- Quest 테이블 하드코딩 방식으로 더미 데이터 삽입
INSERT INTO quest (trainee_id, trainer_id, start_at, end_at)
VALUES
-- Trainee 2 데이터 (trainer_id: 1)
(2, 1, '2024-11-01', '2024-11-02'),
(6, 1, '2024-11-01', '2024-11-02'),
(2, 1, '2024-11-02', '2024-11-03'),
(6, 1, '2024-11-02', '2024-11-03'),
(2, 1, '2024-11-03', '2024-11-04'),
(6, 1, '2024-11-03', '2024-11-04'),
(2, 1, '2024-11-04', '2024-11-05'),
(6, 1, '2024-11-04', '2024-11-05'),
(2, 1, '2024-11-05', '2024-11-06'),
(6, 1, '2024-11-05', '2024-11-06'),
(2, 1, '2024-11-06', '2024-11-07'),
(6, 1, '2024-11-06', '2024-11-07'),
(2, 1, '2024-11-07', '2024-11-08'),
(6, 1, '2024-11-07', '2024-11-08'),
(2, 1, '2024-11-08', '2024-11-09'),
(6, 1, '2024-11-08', '2024-11-09'),
(2, 1, '2024-11-09', '2024-11-10'),
(6, 1, '2024-11-09', '2024-11-10'),
(2, 1, '2024-11-10', '2024-11-11'),
(6, 1, '2024-11-10', '2024-11-11'),
(2, 1, '2024-11-11', '2024-11-12'),
(6, 1, '2024-11-11', '2024-11-12'),
(2, 1, '2024-11-12', '2024-11-13'),
(6, 1, '2024-11-12', '2024-11-13'),
(2, 1, '2024-11-13', '2024-11-14'),
(6, 1, '2024-11-13', '2024-11-14'),
(2, 1, '2024-11-14', '2024-11-15'),
(6, 1, '2024-11-14', '2024-11-15'),
(2, 1, '2024-11-15', '2024-11-16'),
(6, 1, '2024-11-15', '2024-11-16'),
(2, 1, '2024-11-16', '2024-11-17'),
(6, 1, '2024-11-16', '2024-11-17'),
(2, 1, '2024-11-17', '2024-11-18'),
(6, 1, '2024-11-17', '2024-11-18'),
(2, 1, '2024-11-18', '2024-11-19'),
(6, 1, '2024-11-18', '2024-11-19'),
(2, 1, '2024-11-19', '2024-11-20'),
(6, 1, '2024-11-19', '2024-11-20'),
(2, 1, '2024-11-20', '2024-11-21'),
(6, 1, '2024-11-20', '2024-11-21'),
(2, 1, '2024-11-21', '2024-11-22'),
(6, 1, '2024-11-21', '2024-11-22'),
(2, 1, '2024-11-22', '2024-11-23'),
(6, 1, '2024-11-22', '2024-11-23'),
(2, 1, '2024-11-23', '2024-11-24'),
(6, 1, '2024-11-23', '2024-11-24'),
(2, 1, '2024-11-24', '2024-11-25'),
(6, 1, '2024-11-24', '2024-11-25'),
(2, 1, '2024-11-25', '2024-11-26'),
(6, 1, '2024-11-25', '2024-11-26'),
(2, 1, '2024-11-26', '2024-11-27'),
(6, 1, '2024-11-26', '2024-11-27'),
(2, 1, '2024-11-27', '2024-11-28'),
(6, 1, '2024-11-27', '2024-11-28'),
(2, 1, '2024-11-28', '2024-11-29'),
(6, 1, '2024-11-28', '2024-11-29'),
(2, 1, '2024-11-29', '2024-11-30'),
(6, 1, '2024-11-29', '2024-11-30'),
(2, 1, '2024-11-30', '2024-12-01'),
(6, 1, '2024-11-30', '2024-12-01');

-- Task 테이블에 하드코딩 방식으로 더미 데이터 삽입
INSERT INTO task (quest_id, order_index, is_completed, count, weight_kg, cardio_minutes, exercise_id)
VALUES
-- Quest 1 데이터
(1, 1, FALSE, NULL, NULL, 30, 1), -- Cardio: 런닝 머신
(1, 2, TRUE, 20, 60, NULL, 3),   -- Weight: 벤치 프레스

-- Quest 2 데이터
(2, 1, TRUE, 15, 50, NULL, 4),  -- Weight: 딥스
(2, 2, FALSE, NULL, NULL, 25, 2), -- Cardio: 싸이클

-- Quest 3 데이터
(3, 1, TRUE, 12, 70, NULL, 8),  -- Weight: 랫풀다운
(3, 2, FALSE, NULL, NULL, 20, 1), -- Cardio: 런닝 머신
(3, 3, TRUE, 10, 80, NULL, 10), -- Weight: 루마니안 데드리프트

-- Quest 4 데이터
(4, 1, FALSE, 18, 90, NULL, 15), -- Weight: 숄더프레스
(4, 2, TRUE, 8, 40, NULL, 13), -- Weight: 사이드 레터럴 레이즈

-- Quest 5 데이터
(5, 1, FALSE, NULL, NULL, 45, 2), -- Cardio: 싸이클
(5, 2, TRUE, NULL, NULL, 50, 1), -- Cardio: 런닝 머신

-- Quest 6 데이터
(6, 1, FALSE, 5, 20, NULL, 20), -- Weight: 덤벨 리스트 컬
(6, 2, TRUE, 10, 25, NULL, 18), -- Weight: 바벨컬
(6, 3, FALSE, NULL, NULL, 15, 2), -- Cardio: 싸이클

-- Quest 7 데이터
(7, 1, TRUE, 12, 80, NULL, 24), -- Weight: 레그 익스텐션
(7, 2, FALSE, 20, 90, NULL, 23), -- Weight: 레그 프레스
(7, 3, FALSE, NULL, NULL, 40, 1), -- Cardio: 런닝 머신

-- Quest 8 데이터
(8, 1, FALSE, NULL, NULL, 35, 2), -- Cardio: 싸이클
(8, 2, TRUE, 25, 110, NULL, 25), -- Weight: 덤벨 런지
(8, 3, FALSE, NULL, NULL, 20, 1), -- Cardio: 런닝 머신

-- Quest 9 데이터
(9, 1, FALSE, 8, 55, NULL, 17), -- Weight: 프론트레이즈
(9, 2, TRUE, 10, 45, NULL, 16), -- Weight: 숄더프레스

-- Quest 10 데이터
(10, 1, TRUE, 15, 60, NULL, 6), -- Weight: 케이블 크로스 오버
(10, 2, FALSE, 25, 95, NULL, 24), -- Weight: 레그 익스텐션
(10, 3, FALSE, NULL, NULL, 50, 2), -- Cardio: 싸이클

-- Quest 11 데이터
(11, 1, TRUE, 20, 70, NULL, 11), -- Weight: 풀업
(11, 2, FALSE, NULL, NULL, 30, 1), -- Cardio: 런닝 머신

-- Quest 12 데이터
(12, 1, FALSE, 18, 85, NULL, 9), -- Weight: 친업
(12, 2, TRUE, 15, 95, NULL, 10), -- Weight: 루마니안 데드리프트

-- Quest 13 데이터
(13, 1, FALSE, 20, 65, NULL, 5), -- Weight: 덤벨 플라이
(13, 2, TRUE, 25, 75, NULL, 7), -- Weight: 푸시업

-- Quest 14 데이터
(14, 1, TRUE, NULL, NULL, 40, 2), -- Cardio: 싸이클
(14, 2, FALSE, 10, 80, NULL, 12), -- Weight: 티바로우머신

-- Quest 15 데이터
(15, 1, FALSE, 18, 85, NULL, 17), -- Weight: 프론트레이즈
(15, 2, TRUE, NULL, NULL, 45, 1), -- Cardio: 런닝 머신

-- Quest 16 데이터
(16, 1, FALSE, 12, 50, NULL, 22), -- Weight: 트라이셉 익스텐션머신
(16, 2, TRUE, 8, 35, NULL, 18), -- Weight: 바벨컬

-- Quest 17 데이터
(17, 1, TRUE, NULL, NULL, 25, 2), -- Cardio: 싸이클
(17, 2, FALSE, 5, 15, NULL, 21), -- Weight: 암컬 머신

-- Quest 18 데이터
(18, 1, TRUE, 15, 55, NULL, 14), -- Weight: 페이스 풀
(18, 2, FALSE, 20, 60, NULL, 12), -- Weight: 티바로우머신

-- Quest 19 데이터
(19, 1, TRUE, NULL, NULL, 25, 2), -- Cardio: 싸이클
(19, 2, FALSE, 5, 15, NULL, 21), -- Weight: 암컬 머신

-- Quest 20 데이터
(20, 1, FALSE, NULL, NULL, 30, 1), -- Cardio: 런닝 머신
(20, 2, TRUE, 20, 60, NULL, 3),   -- Weight: 벤치 프레스

-- Quest 21 데이터
(21, 1, TRUE, 15, 50, NULL, 4),  -- Weight: 딥스
(21, 2, FALSE, NULL, NULL, 25, 2), -- Cardio: 싸이클

-- Quest 22 데이터
(22, 1, TRUE, 12, 70, NULL, 8),  -- Weight: 랫풀다운
(22, 2, FALSE, NULL, NULL, 20, 1), -- Cardio: 런닝 머신
(22, 3, TRUE, 10, 80, NULL, 10), -- Weight: 루마니안 데드리프트

-- Quest 23 데이터
(23, 1, FALSE, 18, 90, NULL, 15), -- Weight: 숄더프레스
(23, 2, TRUE, 8, 40, NULL, 13), -- Weight: 사이드 레터럴 레이즈

-- Quest 24 데이터
(24, 1, FALSE, NULL, NULL, 45, 2), -- Cardio: 싸이클
(24, 2, TRUE, NULL, NULL, 50, 1), -- Cardio: 런닝 머신

-- Quest 25 데이터
(25, 1, FALSE, 5, 20, NULL, 20), -- Weight: 덤벨 리스트 컬
(25, 2, TRUE, 10, 25, NULL, 18), -- Weight: 바벨컬
(25, 3, FALSE, NULL, NULL, 15, 2), -- Cardio: 싸이클

-- Quest 26 데이터
(26, 1, TRUE, 12, 80, NULL, 24), -- Weight: 레그 익스텐션
(26, 2, FALSE, 20, 90, NULL, 23), -- Weight: 레그 프레스
(26, 3, FALSE, NULL, NULL, 40, 1), -- Cardio: 런닝 머신

-- Quest 27 데이터
(27, 1, FALSE, NULL, NULL, 35, 2), -- Cardio: 싸이클
(27, 2, TRUE, 25, 110, NULL, 25), -- Weight: 덤벨 런지
(27, 3, FALSE, NULL, NULL, 20, 1), -- Cardio: 런닝 머신

-- Quest 28 데이터
(28, 1, FALSE, 8, 55, NULL, 17), -- Weight: 프론트레이즈
(28, 2, TRUE, 10, 45, NULL, 16), -- Weight: 숄더프레스

-- Quest 29 데이터
(29, 1, TRUE, 15, 60, NULL, 6), -- Weight: 케이블 크로스 오버
(29, 2, FALSE, 25, 95, NULL, 24), -- Weight: 레그 익스텐션
(29, 3, FALSE, NULL, NULL, 50, 2), -- Cardio: 싸이클

-- Quest 30 데이터
(30, 1, TRUE, 20, 70, NULL, 11), -- Weight: 풀업
(30, 2, FALSE, NULL, NULL, 30, 1), -- Cardio: 런닝 머신

-- Quest 31 데이터
(31, 1, FALSE, 18, 85, NULL, 9), -- Weight: 친업
(31, 2, TRUE, 15, 95, NULL, 10), -- Weight: 루마니안 데드리프트

-- Quest 32 데이터
(32, 1, FALSE, 20, 65, NULL, 5), -- Weight: 덤벨 플라이
(32, 2, TRUE, 25, 75, NULL, 7), -- Weight: 푸시업

-- Quest 33 데이터
(33, 1, TRUE, NULL, NULL, 40, 2), -- Cardio: 싸이클
(33, 2, FALSE, 10, 80, NULL, 12), -- Weight: 티바로우머신

-- Quest 34 데이터
(34, 1, FALSE, 18, 85, NULL, 17), -- Weight: 프론트레이즈
(34, 2, TRUE, NULL, NULL, 45, 1), -- Cardio: 런닝 머신

-- Quest 35 데이터
(35, 1, FALSE, 12, 50, NULL, 22), -- Weight: 트라이셉 익스텐션머신
(35, 2, TRUE, 8, 35, NULL, 18), -- Weight: 바벨컬

-- Quest 36 데이터
(36, 1, TRUE, NULL, NULL, 25, 2), -- Cardio: 싸이클
(36, 2, FALSE, 5, 15, NULL, 21), -- Weight: 암컬 머신

-- Quest 37 데이터
(37, 1, TRUE, 15, 55, NULL, 14), -- Weight: 페이스 풀
(37, 2, FALSE, 20, 60, NULL, 12), -- Weight: 티바로우머신

-- Quest 38 데이터
(38, 1, TRUE, NULL, NULL, 25, 2), -- Cardio: 싸이클
(38, 2, FALSE, 5, 15, NULL, 21), -- Weight: 암컬 머신

-- Quest 39 데이터
(39, 1, FALSE, NULL, NULL, 30, 1), -- Cardio: 런닝 머신
(39, 2, TRUE, 20, 60, NULL, 3),

-- Quest 40 데이터
(40, 1, TRUE, 15, 50, NULL, 4),  -- Weight: 딥스
(40, 2, FALSE, NULL, NULL, 25, 2), -- Cardio: 싸이클

-- Quest 41 데이터
(41, 1, FALSE, NULL, NULL, 30, 1), -- Cardio: 런닝 머신
(41, 2, TRUE, 20, 60, NULL, 3),   -- Weight: 벤치 프레스

-- Quest 42 데이터
(42, 1, TRUE, 15, 50, NULL, 4),  -- Weight: 딥스
(42, 2, FALSE, NULL, NULL, 25, 2), -- Cardio: 싸이클

-- Quest 43 데이터
(43, 1, TRUE, 12, 70, NULL, 8),  -- Weight: 랫풀다운
(43, 2, FALSE, NULL, NULL, 20, 1), -- Cardio: 런닝 머신
(43, 3, TRUE, 10, 80, NULL, 10), -- Weight: 루마니안 데드리프트

-- Quest 44 데이터
(44, 1, FALSE, 18, 90, NULL, 15), -- Weight: 숄더프레스
(44, 2, TRUE, 8, 40, NULL, 13), -- Weight: 사이드 레터럴 레이즈

-- Quest 45 데이터
(45, 1, FALSE, NULL, NULL, 45, 2), -- Cardio: 싸이클
(45, 2, TRUE, NULL, NULL, 50, 1), -- Cardio: 런닝 머신

-- Quest 46 데이터
(46, 1, FALSE, 5, 20, NULL, 20), -- Weight: 덤벨 리스트 컬
(46, 2, TRUE, 10, 25, NULL, 18), -- Weight: 바벨컬
(46, 3, FALSE, NULL, NULL, 15, 2), -- Cardio: 싸이클

-- Quest 47 데이터
(47, 1, TRUE, 12, 80, NULL, 24), -- Weight: 레그 익스텐션
(47, 2, FALSE, 20, 90, NULL, 23), -- Weight: 레그 프레스
(47, 3, FALSE, NULL, NULL, 40, 1), -- Cardio: 런닝 머신

-- Quest 48 데이터
(48, 1, FALSE, NULL, NULL, 35, 2), -- Cardio: 싸이클
(48, 2, TRUE, 25, 110, NULL, 25), -- Weight: 덤벨 런지
(48, 3, FALSE, NULL, NULL, 20, 1), -- Cardio: 런닝 머신

-- Quest 49 데이터
(49, 1, FALSE, 8, 55, NULL, 17), -- Weight: 프론트레이즈
(49, 2, TRUE, 10, 45, NULL, 16), -- Weight: 숄더프레스

-- Quest 50 데이터
(50, 1, TRUE, 15, 60, NULL, 6), -- Weight: 케이블 크로스 오버
(50, 2, FALSE, 25, 95, NULL, 24), -- Weight: 레그 익스텐션
(50, 3, FALSE, NULL, NULL, 50, 2), -- Cardio: 싸이클

-- Quest 51 데이터
(51, 1, TRUE, 20, 70, NULL, 11), -- Weight: 풀업
(51, 2, FALSE, NULL, NULL, 30, 1), -- Cardio: 런닝 머신

-- Quest 52 데이터
(52, 1, FALSE, 18, 85, NULL, 9), -- Weight: 친업
(52, 2, TRUE, 15, 95, NULL, 10), -- Weight: 루마니안 데드리프트

-- Quest 53 데이터
(53, 1, FALSE, 20, 65, NULL, 5), -- Weight: 덤벨 플라이
(53, 2, TRUE, 25, 75, NULL, 7), -- Weight: 푸시업

-- Quest 54 데이터
(54, 1, TRUE, NULL, NULL, 40, 2), -- Cardio: 싸이클
(54, 2, FALSE, 10, 80, NULL, 12), -- Weight: 티바로우머신

-- Quest 55 데이터
(55, 1, FALSE, 18, 85, NULL, 17), -- Weight: 프론트레이즈
(55, 2, TRUE, NULL, NULL, 45, 1), -- Cardio: 런닝 머신

-- Quest 56 데이터
(56, 1, FALSE, 12, 50, NULL, 22), -- Weight: 트라이셉 익스텐션머신
(56, 2, TRUE, 8, 35, NULL, 18), -- Weight: 바벨컬

-- Quest 57 데이터
(57, 1, TRUE, NULL, NULL, 25, 2), -- Cardio: 싸이클
(57, 2, FALSE, 5, 15, NULL, 21), -- Weight: 암컬 머신

-- Quest 58 데이터
(58, 1, TRUE, 15, 55, NULL, 14), -- Weight: 페이스 풀
(58, 2, FALSE, 20, 60, NULL, 12), -- Weight: 티바로우머신

-- Quest 59 데이터
(59, 1, TRUE, NULL, NULL, 25, 2), -- Cardio: 싸이클
(59, 2, FALSE, 5, 15, NULL, 21), -- Weight: 암컬 머신

-- Quest 60 데이터
(60, 1, FALSE, NULL, NULL, 30, 1), -- Cardio: 런닝 머신
(60, 2, TRUE, 20, 60, NULL, 3);   -- Weight: 벤치 프레스

-- Feedback 테이블에 더미 데이터 삽입
INSERT INTO feedback (quest_id, trainer_id, content)
VALUES
(1, 1, '운동 자세가 좋아지고 있습니다. 조금 더 무게를 늘려보세요.'),
(2, 1, '자세를 조금 더 안정적으로 유지해주세요.'),
(3, 1, '런닝 머신 속도를 조금 더 빠르게 설정해보세요.'),
(5, 1, '오늘 운동 강도가 적절했습니다. 다음에도 비슷한 강도로 진행하세요.'),
(6, 1, '덤벨 컬 시 손목의 각도를 조심하세요. 부상의 위험이 있습니다.'),
(7, 1, '스쿼트 자세가 안정적입니다. 횟수를 조금 더 늘려보세요.'),
(11, 1, '풀업 동작이 좋아졌습니다. 개수를 조금 더 늘려보세요.'),
(12, 1, '루마니안 데드리프트 시 허리 각도를 더 신경써주세요.'),
(14, 1, '페이스 풀 동작에서 속도를 조금 더 줄이세요.'),
(15, 1, '프론트 스쿼트에서 안정감이 좋아졌습니다.'),
(16, 1, '사이드 레터럴 레이즈 시 팔꿈치 각도를 유지하세요.'),
(18, 1, '케이블 크로스 오버 동작에서 팔의 각도를 유지해보세요.'),
(19, 1, '운동 자세가 좋아지고 있습니다. 조금 더 무게를 늘려보세요.'),
(20, 1, '자세를 조금 더 안정적으로 유지해주세요.'),
(21, 1, '런닝 머신 속도를 조금 더 빠르게 설정해보세요.'),
(23, 1, '오늘 운동 강도가 적절했습니다. 다음에도 비슷한 강도로 진행하세요.'),
(24, 1, '덤벨 컬 시 손목의 각도를 조심하세요. 부상의 위험이 있습니다.'),
(25, 1, '스쿼트 자세가 안정적입니다. 횟수를 조금 더 늘려보세요.'),
(29, 1, '풀업 동작이 좋아졌습니다. 개수를 조금 더 늘려보세요.'),
(30, 1, '루마니안 데드리프트 시 허리 각도를 더 신경써주세요.'),
(32, 1, '페이스 풀 동작에서 속도를 조금 더 줄이세요.'),
(33, 1, '프론트 스쿼트에서 안정감이 좋아졌습니다.'),
(34, 1, '사이드 레터럴 레이즈 시 팔꿈치 각도를 유지하세요.'),
(36, 1, '케이블 크로스 오버 동작에서 팔의 각도를 유지해보세요.'),
(38, 1, '런닝 머신 속도를 조금 더 빠르게 설정해보세요.'),
(40, 1, '오늘 운동 강도가 적절했습니다. 다음에도 비슷한 강도로 진행하세요.'),
(41, 1, '덤벨 컬 시 손목의 각도를 조심하세요. 부상의 위험이 있습니다.'),
(42, 1, '스쿼트 자세가 안정적입니다. 횟수를 조금 더 늘려보세요.'),
(43, 1, '풀업 동작이 좋아졌습니다. 개수를 조금 더 늘려보세요.'),
(44, 1, '루마니안 데드리프트 시 허리 각도를 더 신경써주세요.'),
(45, 1, '페이스 풀 동작에서 속도를 조금 더 줄이세요.'),
(46, 1, '프론트 스쿼트에서 안정감이 좋아졌습니다.'),
(47, 1, '사이드 레터럴 레이즈 시 팔꿈치 각도를 유지하세요.'),
(48, 1, '케이블 크로스 오버 동작에서 팔의 각도를 유지해보세요.'),
(49, 1, '페이스 풀 동작에서 속도를 조금 더 줄이세요.'),
(50, 1, '프론트 스쿼트에서 안정감이 좋아졌습니다.'),
(51, 1, '사이드 레터럴 레이즈 시 팔꿈치 각도를 유지하세요.');

SELECT * FROM user;
SELECT * FROM trainer;
SELECT * FROM trainee;
SELECT * FROM quest;
SELECT * FROM exercise;
SELECT * FROM task;
SELECT * FROM feedback;
SELECT * FROM notification;