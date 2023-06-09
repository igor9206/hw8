import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun addNote() {
        val service: NoteService<Notes> = NoteService()
        val note = Note("0", "first", "my")
        val result = service.add(note)

        assertEquals(note.noteId, result)
    }

    @Test
    fun addComment() {
        val service: NoteService<Notes> = NoteService()
        val comment = Comment("0", "first comment", "0")
        val result = service.add(comment)

        assertEquals(comment.commentId, result)
    }

    @Test
    fun delNote() {
        val service: NoteService<Notes> = NoteService()
        val note = Note("0", "first", "my")
        service.add(note)
        val result = service.del(note)

        assertEquals("1", result)
    }

    @Test
    fun delComment() {
        val service: NoteService<Notes> = NoteService()
        val comment = Comment("0", "first comment", "0")
        service.add(comment)
        val result = service.del(comment)

        assertEquals("1", result)
    }

    @Test
    fun editNote() {
        val service: NoteService<Notes> = NoteService()
        val note = Note("0", "first", "my")
        val editNote = Note("0", "EDIT", "EDIT")
        service.add(note)
        val result = service.edit(editNote)

        assertEquals("1", result)
    }

    @Test
    fun editComment() {
        val service: NoteService<Notes> = NoteService()
        val comment = Comment("0", "second comment", "0")
        val editComment = Comment("0", "EDIT comment", "0")
        service.add(comment)
        val result = service.edit(editComment)

        assertEquals("1", result)
    }

    @Test(expected = PostNotFoundException::class)
    fun editCommentBeDel() {
        val service: NoteService<Notes> = NoteService()
        val comment = Comment("0", "second comment", "0", true)
        val editComment = Comment("0", "EDIT comment", "0")
        service.add(comment)
        service.edit(editComment)
    }

    @Test
    fun get() {
        val service: NoteService<Notes> = NoteService()
        val note = Note("0", "first", "my")
        val note2 = Note("1", "second", "name")
        service.add(note)
        service.add(note2)
        val expected = listOf(note, note2)
        val result = service.get()

        assertEquals(expected, result)
    }

    @Test
    fun getById() {
        val service: NoteService<Notes> = NoteService()
        val note = Note("0", "first", "my")
        service.add(note)
        val result = service.getById("0")

        assertEquals(note, result)
    }

    @Test
    fun getComments() {
        val service: NoteService<Notes> = NoteService()
        val comment = Comment("0", "first comment", "0")
        val comment2 = Comment("1", "second comment", "0")
        service.add(comment)
        service.add(comment2)
        val expected = listOf(comment, comment2)
        val result = service.getComments("0")

        assertEquals(expected, result)
    }

    @Test
    fun restoreComment() {
        val service: NoteService<Notes> = NoteService()
        val comment = Comment("0", "first comment", "0")
        service.add(comment)
        service.del(comment)
        val result = service.restoreComment("0")

        assertEquals("1", result)
    }

}