# DOCKER LOCAL
```
docker build -t simi .
docker run -p 8080:8080 simi
```

# .envrc 활성화 
로컬에서 각 프로젝트 별로 환경 변수를 관리하기 위한 방법 

.envrc.example 파일을 복사하여 .envrc 파일을 생성하고 내용을 수정한다.
```
brew install direnv
direnv allow
```

# 