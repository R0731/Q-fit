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
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- trainee 테이블 (훈련생 정보, user와 1:1 관계)
CREATE TABLE trainee (
    id INT PRIMARY KEY,
    trainer_id INT,
    FOREIGN KEY (id) REFERENCES user(id),
    FOREIGN KEY (trainer_id) REFERENCES trainer(id)
);

-- trainer 테이블 (트레이너 정보, user와 1:多 관계)
CREATE TABLE trainer (
    id INT PRIMARY KEY,
    gym VARCHAR(100),
    FOREIGN KEY (id) REFERENCES user(id)
);

-- today_quest 테이블 (훈련생이 받은 퀘스트 정보)
CREATE TABLE today_quest (
    quest_id INT PRIMARY KEY AUTO_INCREMENT,
    trainee_id INT NOT NULL,
    trainer_id INT NOT NULL,
    start_at DATETIME,
    end_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME,
    difficulty ENUM('EASY', 'MEDIUM', 'HARD'),
    FOREIGN KEY (trainee_id) REFERENCES trainee(id),
    FOREIGN KEY (trainer_id) REFERENCES trainer(id)
);

-- task 테이블 (퀘스트 내 개별 작업)
CREATE TABLE task (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    quest_id INT NOT NULL,
    order_index INT,
    is_completed BOOLEAN DEFAULT FALSE,
    exercise_type ENUM('Weight', 'Cardio'),
    count INT,
    weight_kg INT,      -- Weight에서 사용 (무게)
    cardio_minutes INT, -- Cardio에서 사용 (시간)
    FOREIGN KEY (quest_id) REFERENCES today_quest(quest_id)
);

-- review 테이블 (today_quest와 관련된 리뷰)
CREATE TABLE review (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    quest_id INT NOT NULL,
    difficulty ENUM('EASY', 'MEDIUM', 'HARD'),
    content TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quest_id) REFERENCES today_quest(quest_id)
);

-- feedback 테이블 (review에 대한 트레이너의 피드백)
CREATE TABLE feedback (
    feedback_id INT PRIMARY KEY AUTO_INCREMENT,
    quest_id INT NOT NULL,
    trainer_id INT NOT NULL,
    content TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quest_id) REFERENCES today_quest(quest_id),
    FOREIGN KEY (trainer_id) REFERENCES trainer(id)
);

-- notification 테이블 (사용자에게 발송된 알림 정보)
CREATE TABLE notification (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
