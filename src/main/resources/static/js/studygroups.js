const API_BASE = '/api/study-groups';
let currentUserId = 1; // Replace with actual logged-in user ID
let selectedGroupId = null;
let currentPostIdForComment = null;
let allGroups = [];

// Elements
const myGroupsList = document.getElementById('myGroupsList');
const exploreGroupsList = document.getElementById('exploreGroupsList');
const groupDetail = document.getElementById('groupDetail');
const emptyState = document.getElementById('emptyState');
const groupName = document.getElementById('groupName');
const groupDescription = document.getElementById('groupDescription');
const memberCount = document.getElementById('memberCount');
const memberCountSpan = document.getElementById('memberCountSpan');
const membersList = document.getElementById('membersList');
const postsFeed = document.getElementById('postsFeed');
const postTitle = document.getElementById('postTitle');
const postContent = document.getElementById('postContent');
const createPostBtn = document.getElementById('createPostBtn');
const joinBtn = document.getElementById('joinBtn');
const leaveBtn = document.getElementById('leaveBtn');

// Modals
const createGroupModal = document.getElementById('createGroupModal');
const commentModal = document.getElementById('commentModal');

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    console.log('StudyGroups initialized');
    loadMyGroups();
    loadPublicGroups();
    setupEventListeners();
});

// ============= Event Listeners =============

function setupEventListeners() {
    // Create Group Modal
    document.getElementById('openCreateModal').addEventListener('click', openCreateGroupModal);
    document.getElementById('confirmCreateBtn').addEventListener('click', createGroup);
    document.getElementById('cancelCreateBtn').addEventListener('click', closeCreateGroupModal);
    document.querySelector('#createGroupModal .close-modal').addEventListener('click', closeCreateGroupModal);

    // Comment Modal
    document.getElementById('confirmCommentBtn').addEventListener('click', addComment);
    document.getElementById('cancelCommentBtn').addEventListener('click', closeCommentModal);
    document.querySelector('#commentModal .close-modal').addEventListener('click', closeCommentModal);

    // Create Post
    createPostBtn.addEventListener('click', createPost);

    // Group Actions
    joinBtn.addEventListener('click', joinGroup);
    leaveBtn.addEventListener('click', leaveGroup);

    // Search
    document.getElementById('searchGroups').addEventListener('input', searchPublicGroups);

    // Close modals on background click
    window.addEventListener('click', (e) => {
        if (e.target === createGroupModal) closeCreateGroupModal();
        if (e.target === commentModal) closeCommentModal();
    });
}

// ============= Load Data =============

async function loadMyGroups() {
    try {
        const response = await fetch(`${API_BASE}/my-groups/${currentUserId}`);
        if (!response.ok) throw new Error('Failed to load groups');

        const groups = await response.json();
        renderGroupsList(groups, myGroupsList, true);
    } catch (error) {
        console.error('Error loading my groups:', error);
        myGroupsList.innerHTML = '<p style="color: #ef4444;">Failed to load groups</p>';
    }
}

async function loadPublicGroups() {
    try {
        const response = await fetch(`${API_BASE}/all-public`);
        if (!response.ok) throw new Error('Failed to load public groups');

        const groups = await response.json();
        allGroups = groups;
        renderGroupsList(groups, exploreGroupsList, false);
    } catch (error) {
        console.error('Error loading public groups:', error);
        exploreGroupsList.innerHTML = '<p style="color: #ef4444;">Failed to load groups</p>';
    }
}

function renderGroupsList(groups, container, isMine) {
    container.innerHTML = '';

    if (groups.length === 0) {
        container.innerHTML = `<p style="color: #94a3b8; font-size: 0.9rem;">No groups yet</p>`;
        return;
    }

    groups.forEach(group => {
        const div = document.createElement('div');
        div.className = 'group-item';
        div.setAttribute('data-group-id', group.id);
        div.onclick = () => selectGroup(group.id);

        const memberCount = group.members ? group.members.length : 0;
        div.innerHTML = `
            <div class="group-name">${escapeHtml(group.name)}</div>
            <div class="group-members">👥 ${memberCount} member${memberCount !== 1 ? 's' : ''}</div>
        `;
        container.appendChild(div);
    });
}

function selectGroup(groupId) {
    selectedGroupId = groupId;

    // Update UI
    document.querySelectorAll('.group-item').forEach(item => item.classList.remove('active'));
    document.querySelector(`[data-group-id="${groupId}"]`)?.classList.add('active');

    groupDetail.classList.add('active');
    emptyState.classList.add('hidden');

    // Load group details
    loadGroupDetails();
    loadGroupPosts();
}

async function loadGroupDetails() {
    try {
        const response = await fetch(`${API_BASE}/${selectedGroupId}`);
        if (!response.ok) throw new Error('Failed to load group');

        const group = await response.json();

        groupName.textContent = escapeHtml(group.name);
        groupDescription.textContent = escapeHtml(group.description);

        const memberLength = group.members ? group.members.length : 0;
        memberCount.textContent = `${memberLength} member${memberLength !== 1 ? 's' : ''}`;
        memberCountSpan.textContent = memberLength;

        // Check if user is member
        const isMember = group.members && group.members.some(m => m.id === currentUserId);
        joinBtn.style.display = isMember ? 'none' : 'block';
        leaveBtn.style.display = isMember ? 'block' : 'none';

        // Render members
        membersList.innerHTML = '';
        if (group.members && group.members.length > 0) {
            group.members.forEach(member => {
                const div = document.createElement('div');
                div.className = 'member-card';
                const initials = member.name ? member.name.split(' ').map(n => n[0]).join('').toUpperCase() : 'U';
                div.innerHTML = `
                    <div class="member-avatar">${initials}</div>
                    <div class="member-name">${escapeHtml(member.name)}</div>
                    <div class="member-email">${escapeHtml(member.email)}</div>
                `;
                membersList.appendChild(div);
            });
        }
    } catch (error) {
        console.error('Error loading group details:', error);
        groupName.textContent = 'Error loading group';
    }
}

async function loadGroupPosts() {
    try {
        const response = await fetch(`${API_BASE}/${selectedGroupId}/posts`);
        if (!response.ok) throw new Error('Failed to load posts');

        const posts = await response.json();

        postsFeed.innerHTML = '';

        if (posts.length === 0) {
            postsFeed.innerHTML = '<p style="color: #94a3b8; text-align: center; padding: 2rem;">No posts yet. Be the first to start a discussion!</p>';
            return;
        }

        posts.forEach(post => {
            const postDiv = document.createElement('div');
            postDiv.className = 'post-card';
            const authorInitials = post.author.name ? post.author.name.split(' ').map(n => n[0]).join('').toUpperCase() : 'U';
            const postDate = new Date(post.createdAt).toLocaleDateString('en-US', {
                month: 'short',
                day: 'numeric',
                year: 'numeric'
            });

            postDiv.innerHTML = `
                <div class="post-header">
                    <div class="post-author">
                        <div class="post-avatar">${authorInitials}</div>
                        <div class="post-meta">
                            <div class="post-author-name">${escapeHtml(post.author.name)}</div>
                            <div class="post-time">${postDate}</div>
                        </div>
                    </div>
                </div>
                <div class="post-title">${escapeHtml(post.title)}</div>
                <div class="post-content">${escapeHtml(post.content)}</div>
                <div class="post-actions">
                    <button class="action-btn" onclick="likePost(${post.id})">👍 Like <span>(${post.likes})</span></button>
                    <button class="action-btn" onclick="openCommentModal(${post.id})">💬 Comment</button>
                </div>
                <div id="comments-${post.id}" class="comments-section"></div>
            `;
            postsFeed.appendChild(postDiv);
            loadComments(post.id);
        });
    } catch (error) {
        console.error('Error loading posts:', error);
        postsFeed.innerHTML = '<p style="color: #ef4444;">Failed to load posts</p>';
    }
}

async function loadComments(postId) {
    try {
        const response = await fetch(`${API_BASE}/posts/${postId}/comments`);
        if (!response.ok) throw new Error('Failed to load comments');

        const comments = await response.json();

        const commentsContainer = document.getElementById(`comments-${postId}`);
        commentsContainer.innerHTML = '';

        if (comments.length === 0) return;

        comments.forEach(comment => {
            const commentDiv = document.createElement('div');
            commentDiv.className = 'comment';
            let codeHtml = '';
            if (comment.codeSnippet) {
                codeHtml = `<pre class="comment-code"><code>${escapeHtml(comment.codeSnippet)}</code></pre>`;
            }
            commentDiv.innerHTML = `
                <div class="comment-author">${escapeHtml(comment.author.name)}</div>
                <div class="comment-text">${escapeHtml(comment.content)}</div>
                ${codeHtml}
            `;
            commentsContainer.appendChild(commentDiv);
        });
    } catch (error) {
        console.error('Error loading comments:', error);
    }
}

// ============= Create Operations =============

async function createGroup() {
    const name = document.getElementById('groupNameInput').value.trim();
    const description = document.getElementById('groupDescInput').value.trim();
    const visibility = document.querySelector('input[name="visibility"]:checked').value;

    if (!name) {
        alert('Please enter group name');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/create`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                creatorId: currentUserId,
                name,
                description,
                visibility
            })
        });

        if (!response.ok) throw new Error('Failed to create group');

        alert('✅ Group created successfully!');
        closeCreateGroupModal();
        loadMyGroups();
        loadPublicGroups();
    } catch (error) {
        console.error('Error creating group:', error);
        alert('❌ Failed to create group');
    }
}

async function createPost() {
    if (!selectedGroupId) {
        alert('Please select a group first');
        return;
    }

    const title = postTitle.value.trim();
    const content = postContent.value.trim();

    if (!title || !content) {
        alert('Please enter both title and content');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/${selectedGroupId}/posts`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                authorId: currentUserId,
                title,
                content
            })
        });

        if (!response.ok) throw new Error('Failed to create post');

        postTitle.value = '';
        postContent.value = '';
        loadGroupPosts();
    } catch (error) {
        console.error('Error creating post:', error);
        alert('❌ Failed to create post');
    }
}

async function addComment() {
    const content = document.getElementById('commentText').value.trim();
    const codeSnippet = document.getElementById('codeSnippet').value.trim();
    const codeLanguage = document.getElementById('codeLanguage').value;

    if (!content) {
        alert('Please enter a comment');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/posts/${currentPostIdForComment}/comments`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                authorId: currentUserId,
                content,
                codeSnippet: codeSnippet || null,
                codeLanguage
            })
        });

        if (!response.ok) throw new Error('Failed to add comment');

        closeCommentModal();
        loadGroupPosts();
    } catch (error) {
        console.error('Error adding comment:', error);
        alert('❌ Failed to add comment');
    }
}

// ============= Group Actions =============

async function joinGroup() {
    try {
        const response = await fetch(`${API_BASE}/${selectedGroupId}/join?userId=${currentUserId}`, {
            method: 'POST'
        });

        if (!response.ok) throw new Error('Failed to join group');

        loadGroupDetails();
        loadMyGroups();
    } catch (error) {
        console.error('Error joining group:', error);
        alert('❌ Failed to join group');
    }
}

async function leaveGroup() {
    if (!confirm('Are you sure you want to leave this group?')) return;

    try {
        const response = await fetch(`${API_BASE}/${selectedGroupId}/leave?userId=${currentUserId}`, {
            method: 'DELETE'
        });

        if (!response.ok) throw new Error('Failed to leave group');

        selectedGroupId = null;
        groupDetail.classList.remove('active');
        emptyState.classList.remove('hidden');
        loadMyGroups();
    } catch (error) {
        console.error('Error leaving group:', error);
        alert('❌ Failed to leave group');
    }
}

async function likePost(postId) {
    try {
        const response = await fetch(`${API_BASE}/posts/${postId}/like`, {
            method: 'POST'
        });

        if (!response.ok) throw new Error('Failed to like post');

        loadGroupPosts();
    } catch (error) {
        console.error('Error liking post:', error);
    }
}

// ============= Search =============

async function searchPublicGroups(e) {
    const query = e.target.value.trim();

    if (!query) {
        renderGroupsList(allGroups, exploreGroupsList, false);
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/search?query=${encodeURIComponent(query)}`);
        if (!response.ok) throw new Error('Search failed');

        const groups = await response.json();
        renderGroupsList(groups, exploreGroupsList, false);
    } catch (error) {
        console.error('Error searching groups:', error);
        exploreGroupsList.innerHTML = '<p style="color: #ef4444;">Search failed</p>';
    }
}

// ============= Modal Management =============

function openCreateGroupModal() {
    createGroupModal.classList.add('active');
    document.getElementById('groupNameInput').focus();
}

function closeCreateGroupModal() {
    createGroupModal.classList.remove('active');
    document.getElementById('groupNameInput').value = '';
    document.getElementById('groupDescInput').value = '';
}

function openCommentModal(postId) {
    currentPostIdForComment = postId;
    commentModal.classList.add('active');
    document.getElementById('commentText').focus();
}

function closeCommentModal() {
    commentModal.classList.remove('active');
    document.getElementById('commentText').value = '';
    document.getElementById('codeSnippet').value = '';
}

// ============= Utility Functions =============

function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}
