# social-news-spring

A social news website that is very similar to Reddit. More details on the features to come.

## Security
Authentication done in Spring Boot using Spring Security and Jwt tokens. User must sign in, and then activate an email sent to him/her to enable account. The user can then log in.

## Improvements
- Using in memory db H2. Would be better to move to PostgreSQL or MySQL.
- Activation mail sent sometimes fails. Workaround is to enter in activation link manually
