import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import { fetchArticleById } from '../../services/api';
import './ArticleDetail.css';

// Styles moved to ArticleDetail.css

const ArticleDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const [article, setArticle] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadArticle = async () => {
      try {
        const data = await fetchArticleById(id);
        setArticle(data);
      } catch (err) {
        setError('Failed to load article.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadArticle();
  }, [id]);


  const handleBack = () => {
    navigate('/');
  };

  return (
    <div className="article-detail-container">
      <div className="article-detail-card">
        {loading ? (
          <h1 className="article-title">Loading...</h1>
        ) : error ? (
          <h1 className="article-title">{error}</h1>
        ) : (
          <>
            <h1 className="article-title">{article?.title}</h1>

            <div className="media-section">
              {article.youtubeId ? (
                <div className="youtube-wrapper">
                  <iframe
                    src={`https://www.youtube.com/embed/${article.youtubeId}`}
                    title={article.title || 'YouTube video'}
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                    allowFullScreen
                  />
                </div>
              ) : (
                <div className="media-placeholder">{article.image || 'Image/Video'}</div>
              )}
            </div>

            <div className="article-meta">
              <div className="author">
                <div className="author-avatar">{(article.author || 'D')[0]}</div>
                <span>{article.author || 'Daniel'}</span>
              </div>
            </div>

            <div className="article-text">
              {String(article.content || '')
                .split(/\n+/)
                .map((p, i) => (
                  <p key={i}>{p}</p>
                ))}
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default ArticleDetail;



