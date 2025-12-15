"""Utility to read and display log files.

Usage:
    python read_log.py path/to/logfile [--lines N] [--follow]

Functions:
- read_log(path, lines=None): returns the last `lines` lines (or entire file if None).
- follow(path): yields new lines appended to the file (like tail -f).

Small CLI is provided for quick use.
"""
import time
import argparse
import os
import sys


def read_log(path, lines=None):
    """Read the log file at `path` and return lines as a list (no trailing newlines).

    Args:
        path (str): filesystem path to log file
        lines (int|None): if provided, return only the last `lines` lines

    Returns:
        list[str]: lines from the log file

    Raises:
        FileNotFoundError: if path does not exist
        PermissionError: if file is not readable
    """
    if not os.path.exists(path):
        raise FileNotFoundError(f"Log file not found: {path}")
    with open(path, "r", encoding="utf-8", errors="replace") as f:
        if lines is None:
            content = f.read().splitlines()
            return content
        else:
            # Efficiently read last N lines by seeking from end if file is large
            try:
                return _read_last_lines(f, lines)
            except Exception:
                # fallback to simple approach
                f.seek(0)
                return f.read().splitlines()[-lines:]


def _read_last_lines(fileobj, lines):
    """Read last `lines` lines from open file object. Returns list of lines.
    Keeps memory usage small by reading from end in blocks.
    """
    assert lines >= 0
    if lines == 0:
        return []
    avg_line_length = 100
    to_read = lines * avg_line_length
    # Try to seek from end
    fileobj.seek(0, os.SEEK_END)
    file_size = fileobj.tell()
    block_size = 1024
    data = ''
    pos = file_size
    while len(data.splitlines()) <= lines and pos > 0:
        read_size = min(block_size, pos)
        pos -= read_size
        fileobj.seek(pos)
        chunk = fileobj.read(read_size)
        data = chunk + data
        if pos == 0:
            break
    return data.splitlines()[-lines:]


def follow(path):
    """Yield new lines appended to the file at `path`.
    Similar to `tail -f`.
    """
    with open(path, "r", encoding="utf-8", errors="replace") as f:
        # Go to the end of file
        f.seek(0, os.SEEK_END)
        while True:
            line = f.readline()
            if not line:
                time.sleep(0.2)
                continue
            yield line.rstrip('\n')


def main():
    parser = argparse.ArgumentParser(description="Read and display log files.")
    parser.add_argument("path", help="Path to the log file")
    parser.add_argument("--lines", "-n", type=int, default=None, help="Show only the last N lines")
    parser.add_argument("--follow", "-f", action="store_true", help="Follow the file (like tail -f)")
    args = parser.parse_args()

    try:
        if args.follow:
            for line in follow(args.path):
                print(line)
        else:
            lines = read_log(args.path, lines=args.lines)
            for l in lines:
                print(l)
    except FileNotFoundError as e:
        print(e, file=sys.stderr)
        sys.exit(2)
    except PermissionError as e:
        print(e, file=sys.stderr)
        sys.exit(3)


if __name__ == '__main__':
    main()
