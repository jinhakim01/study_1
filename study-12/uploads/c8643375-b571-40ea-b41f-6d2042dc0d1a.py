import pandas as pd
from sqlalchemy import create_engine, Column, Integer, String, Boolean, DateTime
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

# PostgreSQL 연결 정보 설정
DB_USER = "root"
DB_PASSWORD = "rootpw"
DB_HOST = "localhost"
DB_PORT = "5432"  # PostgreSQL 기본 포트
DB_NAME = "mydb"

DATABASE_URL = f"postgresql+psycopg2://{DB_USER}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}"

# SQLAlchemy 엔진 및 세션 생성
engine = create_engine(DATABASE_URL, echo=True)
Session = sessionmaker(bind=engine)
session = Session()

# SQLAlchemy ORM 모델 정의
Base = declarative_base()

class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(50))
    phone = Column(String(20))
    address = Column(String(255))
    address_detail = Column(String(255))
    user_status = Column(String(20))
    user_class = Column(String(20))
    email = Column(String(100))
    marketing_agree = Column(Boolean)
    social_login = Column(String(20))
    last_logged_at = Column(DateTime)
    created_at = Column(DateTime)

# 테이블 생성
Base.metadata.create_all(engine)

# CSV 파일 읽기
csv_file = "user.csv"  # CSV 파일 경로
df = pd.read_csv(csv_file, parse_dates=["last_logged_at", "created_at"])

# Boolean 변환 (0/1을 True/False로 변환)
df["marketing_agree"] = df["marketing_agree"].astype(bool)

# 데이터 삽입
users = [User(**row) for row in df.to_dict(orient="records")]
session.bulk_save_objects(users)
session.commit()

print("데이터가 성공적으로 PostgreSQL에 삽입되었습니다!")