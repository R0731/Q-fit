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

INSERT INTO user (user_id, user_pw, user_name, phone_number, email, gender, birth_date, user_img, user_type)
VALUES 
('ssafy', 'ssafy', '트레이너싸피', '010-1234-5678', 'alice@example.com', 'Female', '1990-05-15', 'alice.jpg', 1),
('ssafy2', 'ssafy2', '트레이니싸피', '010-1234-5678', 'alice@example.com', 'Female', '1990-05-15', 'alice.jpg', 2),
('user001', 'password123', 'Alice Smith', '010-1234-5678', 'alice@example.com', 'Female', '1990-05-15', 'alice.jpg', 2),
('user002', 'password456', 'Bob Brown', '010-8765-4321', 'bob@example.com', 'Male', '1985-11-23', 'bob.jpg', 1),
('user003', 'password789', 'Charlie Johnson', '010-5555-6666', 'charlie@example.com', 'Male', '1992-07-08', 'charlie.jpg', 1),
('user004', 'password789', 'Charlie Johnson', '010-5555-6666', 'charlie@example.com', 'Male', '1992-07-08', 'charlie.jpg', 2);

INSERT INTO trainer (id, gym)
VALUES 
(2, 'Fitness Center A'),
(3, 'Fitness Center B'),
(1, 'Fitness Center C');

INSERT INTO trainee (id, trainer_id)
VALUES 
(1, 2),
(2, 1),
(3, 2),
(6, 1);

-- INSERT INTO quest (trainee_id, trainer_id, start_at, end_at)
-- VALUES 
-- (1, 2, '2023-10-31 10:00:00', '2023-10-31 11:00:00'),
-- (2, 1, '2023-10-31 14:00:00', '2023-10-31 15:30:00'),
-- (3, 2, '2023-10-31 09:00:00', '2023-10-31 10:30:00');

-- INSERT INTO task (quest_id, order_index, is_completed, count, weight_kg, cardio_minutes, exercise_id)
-- VALUES 
-- (1, 1, FALSE, 10, 50, NULL, 1),
-- (2, 1, FALSE, NULL, NULL, 20, 2),
-- (3, 1, TRUE, 15, 60, NULL, 3);

INSERT INTO quest (trainee_id, trainer_id, start_at, end_at)
VALUES 
-- 11월 18일 (어제)
(1, 2, '2024-11-18 10:00:00', '2024-11-18 11:00:00'),
(2, 3, '2024-11-18 15:00:00', '2024-11-18 16:00:00'),

-- 11월 19일 (오늘)
(2, 1, '2024-11-19 09:30:00', '2024-11-19 10:30:00'),
(1, 2, '2024-11-19 14:00:00', '2024-11-19 15:00:00'),

-- 11월 20일 (내일)
(2, 3, '2024-11-20 08:00:00', '2024-11-20 09:00:00'),
(3, 2, '2024-11-20 16:30:00', '2024-11-20 17:30:00');

INSERT INTO task (quest_id, order_index, is_completed, count, weight_kg, cardio_minutes, exercise_id)
VALUES 
-- 11월 18일 (어제)
(1, 1, FALSE, 12, 40, NULL, 3), -- 벤치 프레스
(1, 2, FALSE, 10, 50, NULL, 4), -- 딥스
(2, 1, TRUE, NULL, NULL, 20, 1), -- 런닝 머신

-- 11월 19일 (오늘)
(3, 1, FALSE, 15, 60, NULL, 5), -- 덤벨 플라이
(3, 2, FALSE, NULL, NULL, 25, 2), -- 싸이클
(4, 1, TRUE, 20, 70, NULL, 6), -- 케이블 크로스 오버
(4, 2, FALSE, 10, 45, NULL, 8), -- 랫풀다운

-- 11월 20일 (내일)
(5, 1, TRUE, 20, 70, NULL, 9), -- 친업
(5, 2, FALSE, 8, 40, NULL, 11), -- 풀업
(6, 1, TRUE, NULL, NULL, 30, 13), -- 오버헤드프레스
(6, 2, TRUE, 12, 60, NULL, 18); -- 바벨컬


SELECT * FROM task;

INSERT INTO review (quest_id, trainee_id, difficulty)
VALUES 
(1, 1, 'MEDIUM'),
(2, 2, 'HARD'),
(3, 3, 'EASY');

INSERT INTO feedback (quest_id, trainer_id, content)
VALUES 
(1, 2, 'Good effort, Alice! Keep it up!'),
(2, 1, 'You did well, Bob. Let’s push harder next time!'),
(3, 2, 'Great job, Charlie. Remember to stretch after the workout.');

INSERT INTO notification (user_id, message, is_read)
VALUES 
(1, 'Your training session starts in 1 hour.', FALSE),
(2, 'New feedback from your trainer.', TRUE),
(3, 'A new review is available for your last session.', FALSE);

SELECT * FROM user;
SELECT * FROM exercise;
DESCRIBE user; 
SELECT * FROM feedback;
SELECT * FROM quest;
