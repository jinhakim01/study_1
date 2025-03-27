import pandas as pd
from sqlalchemy import create_engine, Column, Integer, String, Boolean, DateTime
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
import pymysql

# MySQL 연결 정보 설정
DB_USER = "root"
DB_PASSWORD = "rootpw"
DB_HOST = "localhost"
DB_PORT = "3306"
DB_NAME = "mydb"

DATABASE_URL = f"mysql+pymysql://{DB_USER}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}"

# SQLAlchemy 엔진 및 세션 생성
engine = create_engine(DATABASE_URL, echo=True)
Session = sessionmaker(bind=engine)
session = Session()

# SQLAlchemy ORM 모델 정의
Base = declarative_base()

class User(Base):
    __tablename__ = "user"

    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(10))
    phone = Column(String(15))
    address = Column(String(255))
    address_detail = Column(String(255))
    user_status = Column(String(6))
    user_class = Column(String(10))
    email = Column(String(50))
    marketing_agree = Column(Boolean)
    social_login = Column(String(10))
    last_logged_at = Column(DateTime)
    created_at = Column(DateTime)

# 테이블 생성
Base.metadata.create_all(engine)

# CSV 파일 읽기
csv_file = "user.csv"  # CSV 파일명
df = pd.read_csv(csv_file, parse_dates=["last_logged_at", "created_at"])

# Boolean 변환 (Pandas는 기본적으로 0/1을 int로 읽음)
df["marketing_agree"] = df["marketing_agree"].astype(bool)

temp = df.to_dict(orient="records")
print(temp)
# 데이터 삽입
users = [User(**row) for row in df.to_dict(orient="records")]
session.bulk_save_objects(users)
session.commit()

print("데이터가 성공적으로 삽입되었습니다!")