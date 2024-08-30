ALTER TABLE posts
ADD CONSTRAINT fk_author
FOREIGN KEY (author_id)
REFERENCES users(id);